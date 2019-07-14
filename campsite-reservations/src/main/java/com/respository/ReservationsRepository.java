package com.respository;

import com.internal.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, String> {

  @Modifying
  @Query(value = "DELETE from RESERVATION r where r.uuid=:reservationUUID", nativeQuery = true)
  public void deleteReservation(@Param("reservationUUID") String reservationUUID);


  @Modifying
  @Query(value = "select * from RESERVATION r WHERE campsite_id =:campsite_id", nativeQuery = true)
  public List<Reservation> getReservationsForCampsite(@Param("campsite_id") Long campsiteID);

  @Modifying
  @Query(value = "select * from RESERVATION r WHERE r.uuid=:reservationUUID", nativeQuery = true)
  public List<Reservation> getReservationFromUUID(@Param("reservationUUID") String reservationUUID);
}
