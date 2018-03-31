package com.fabiang.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabiang.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
