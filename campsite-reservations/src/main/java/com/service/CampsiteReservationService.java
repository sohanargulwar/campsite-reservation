package com.service;

import com.internal.model.Campsite;
import com.internal.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface CampsiteReservationService {

  public void deleteReservation(String reservationUUID);

  public Boolean verifyIfBookingOverlaps(LocalDate arrivalDate, LocalDate departureDate, Long campsiteID);

  public Campsite getCampsite(Long campsiteID);

  public void saveOrUpdateReservation(Reservation reservation);

  public Reservation getReservationFromUUID(String reservationUUID);

  public boolean verifyIfReservationExists(String reservationUUID);

  public List<Reservation> getAllReservationsForCampsite(Long campsiteID);
}
