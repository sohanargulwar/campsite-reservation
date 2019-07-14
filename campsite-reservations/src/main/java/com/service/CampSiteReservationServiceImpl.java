package com.service;

import com.internal.model.Campsite;
import com.internal.model.Reservation;
import com.respository.CampsiteRepository;
import com.respository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Service
public class CampSiteReservationServiceImpl implements CampsiteReservationService {

  @Autowired
  private CampsiteRepository campsiteRepository;

  @Autowired
  private ReservationsRepository reservationsRepository;

  @Transactional
  public void deleteReservation(String reservationUUID) {

    reservationsRepository.deleteReservation(reservationUUID);
  }

  @Override
  public Boolean verifyIfBookingOverlaps(@NotNull LocalDate arrivalDate, @NotNull LocalDate departureDate,
      @NotNull Long campsiteID) {

    final List<Reservation> allReservationsForCampsite = reservationsRepository.getReservationsForCampsite(campsiteID);

    if (CollectionUtils.isEmpty(allReservationsForCampsite)) {
      return false;
    }
    for(Reservation reservation : allReservationsForCampsite) {

      if(!(arrivalDate.isAfter(reservation.getDepartureDate()) ||
          departureDate.isBefore(reservation.getArrivalDate()))) {
        return true;
      }
    }
    return false;
  }

  @Override
  @Transactional
  public Campsite getCampsite(@NotNull Long campsiteID) {
    return campsiteRepository.getOne(campsiteID);
  }

  @Override
  @Transactional
  public void saveOrUpdateReservation(@NotNull Reservation reservation) {
    reservationsRepository.save(reservation);
  }

  @Override
  @Transactional
  public Reservation getReservationFromUUID(@NotNull String reservationUUID) {
    return reservationsRepository.getReservationFromUUID(
        reservationUUID).stream().findFirst().get();
  }

  @Override
  @Transactional
  public boolean verifyIfReservationExists(String reservationUUID) {
    return reservationsRepository.existsById(reservationUUID);
  }

  @Override
  @Transactional
  public List<Reservation> getAllReservationsForCampsite(Long campsiteID) {
    return reservationsRepository.getReservationsForCampsite(campsiteID);
  }
}
