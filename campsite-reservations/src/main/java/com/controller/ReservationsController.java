package com.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.internal.model.Campsite;
import com.internal.model.JsonViews;
import com.internal.model.Response;
import com.service.CampsiteReservationService;
import com.internal.model.Reservation;
import com.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Api(value="Reservations controller", description="Managing reservation")
@RestController
@RequestMapping(Constants.API_ENDPOINT)
public class ReservationsController {

  @Autowired
  private CampsiteReservationService campsiteReservationService;

  @ResponseBody
  @GetMapping(value = Constants.GET_ALL_RESERVATION_ENDPOINT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(JsonViews.ShowDatesOnly.class)
  @ApiOperation(value = "Returns all reservations for a campsite")
  public List<Reservation> getAllReservationForCampsite(@PathVariable(name = "campsiteID") Long CampsiteID) {

    return campsiteReservationService.getAllReservationsForCampsite(CampsiteID);
  }

  @ResponseBody
  @GetMapping
  @RequestMapping(value = Constants.GET_RESERVATION_ENDPOINT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Retrieve the reservation from reservationUUID.")
  public Reservation getReservationByUUID(@PathVariable(name = "reservationUUID") String reservationUUID) {

    return campsiteReservationService.getReservationFromUUID(reservationUUID);
  }

  @GetMapping(value = Constants.DELETE_RESERVATION_ENDPOINT,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Response> deleteReservations(@PathVariable(name = "reservationUUID") String reservationUUID) {

    if (campsiteReservationService.verifyIfReservationExists(reservationUUID)) {

      campsiteReservationService.deleteReservation(reservationUUID);
      return new ResponseEntity<>(new Response().builder()
          .successMessage(String.format("Reservation with reservationUUID %s deleted", reservationUUID))
          .build(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new Response().builder()
          .errorMessage(String.format("Reservation with reservationUUID %s does not exists", reservationUUID))
          .build(), HttpStatus.OK);
    }
  }

  @PostMapping
  @RequestMapping(value = Constants.MAKE_RESERVATION_ENDPOINT,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Create the reservation for specified dates with campsite ID for a user")
  @ResponseBody
  public ResponseEntity<Response> makeReservation(@NotNull @Valid @RequestBody Reservation reservationRequest) {

    final LocalDate arrivalDate = reservationRequest.getArrivalDate();
    final LocalDate departureDate = reservationRequest.getDepartureDate();

    if (!areReservationDaysValid(arrivalDate, departureDate)) {

      return new ResponseEntity<>(new Response().builder()
          .errorMessage(String.format("Reservation should be within next day and %s days", Constants.RESERVATION_OPEN_FOR_NEXT_DAYS))
          .build(), HttpStatus.OK);
    }
    if (!isReservationDaysCriteriaSatisfied(arrivalDate, departureDate)) {

      return new ResponseEntity<>(new Response().builder()
          .errorMessage(String.format("Reservation should be between min %s and max %s days",
              Constants.RESERVATION_MIN_DAYS + 1, Constants.RESERVATION_MAX_DAYS + 1))
          .build(), HttpStatus.OK);
    }

    if (campsiteReservationService.verifyIfBookingOverlaps(arrivalDate, departureDate,
        reservationRequest.getCampsite().getId())) {

      return new ResponseEntity<>(new Response().builder()
          .errorMessage(String.format("Reservation dates conflict. Campsite is already booked on  %s arrival and %s departure date.",
              reservationRequest.getArrivalDate(), reservationRequest.getDepartureDate()))
          .build(), HttpStatus.OK);
    }
    Campsite campsite = Optional.ofNullable(campsiteReservationService.getCampsite(reservationRequest.getCampsite().getId()))
        .orElseThrow(() -> new IllegalStateException("No campsite present"));

    Reservation reservation = new Reservation()
        .builder()
        .reservationUUID(UUID.randomUUID().toString())
        .arrivalDate(reservationRequest.getArrivalDate())
        .departureDate(reservationRequest.getDepartureDate())
        .firstName(reservationRequest.getFirstName())
        .lastName(reservationRequest.getLastName())
        .emailID(reservationRequest.getEmailID())
        .campsite(campsite)
        .build();
    campsiteReservationService.saveOrUpdateReservation(reservation);

    return new ResponseEntity<>(new Response().builder().reservationUUID(reservation.getReservationUUID()).build(), HttpStatus.OK);
  }

  @PutMapping
  @RequestMapping(value = Constants.UPDATE_RESERVATION_ENDPOINT,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Updates the reservation from reservationUUID")
  public ResponseEntity<Response> updateReservation(@NotNull @Valid
      @PathVariable(name = "reservationUUID") String reservationUUID,
      @NotNull @Valid @RequestBody Reservation reservationRequest) {

    final Reservation reservation = campsiteReservationService.getReservationFromUUID(reservationUUID);

    final LocalDate arrivalDate = reservationRequest.getArrivalDate();
    final LocalDate departureDate = reservationRequest.getDepartureDate();

    if (reservation == null) {
      return new ResponseEntity<>(new Response().builder()
          .errorMessage(String.format("Reservation with %s not found", reservationUUID))
          .build(), HttpStatus.OK);
    }

    if (!areReservationDaysValid(arrivalDate, departureDate)) {

      return new ResponseEntity<>(new Response().builder()
          .errorMessage(String.format("Reservation should be within next day and %s days", Constants.RESERVATION_OPEN_FOR_NEXT_DAYS))
          .build(), HttpStatus.OK);
    }
    if (!isReservationDaysCriteriaSatisfied(arrivalDate, departureDate)) {

      return new ResponseEntity<>(new Response().builder()
          .errorMessage(String.format("Reservation should be between min %s and max %s days",
              Constants.RESERVATION_MIN_DAYS + 1, Constants.RESERVATION_MAX_DAYS + 1))
          .build(), HttpStatus.OK);
    }

    if (campsiteReservationService.verifyIfBookingOverlaps(arrivalDate, departureDate,
        reservation.getCampsite().getId())) {

      return new ResponseEntity<>(new Response().builder()
          .errorMessage(String.format("Reservation dates conflict. Campsite is already booked on  %s arrival and %s departure date.",
              reservationRequest.getArrivalDate(), reservationRequest.getDepartureDate()))
          .build(), HttpStatus.OK);
    }

    reservation.setArrivalDate(reservationRequest.getArrivalDate());
    reservation.setDepartureDate(reservationRequest.getDepartureDate());

    campsiteReservationService.saveOrUpdateReservation(reservation);
    return new ResponseEntity<>(new Response().builder()
        .successMessage("Reservation updated successfully")
        .build(), HttpStatus.OK);
  }

  private boolean isReservationDaysCriteriaSatisfied(LocalDate arrivalDate,
      LocalDate departureDate) {

    final Long reservationRequestDays = ChronoUnit.DAYS.between(arrivalDate, departureDate);
    return !(reservationRequestDays > Constants.RESERVATION_MAX_DAYS ||
        reservationRequestDays < Constants.RESERVATION_MIN_DAYS);
  }

  private boolean areReservationDaysValid(LocalDate arrivalDate,
      LocalDate departureDate) {

    final LocalDate upperLimitDate = LocalDate.now().plusDays(Constants.RESERVATION_OPEN_FOR_NEXT_DAYS);
    final LocalDate lowerLimitDate = LocalDate.now().plusDays(1);
    return !arrivalDate.isBefore(lowerLimitDate) || departureDate.isAfter(upperLimitDate);
  }

}
