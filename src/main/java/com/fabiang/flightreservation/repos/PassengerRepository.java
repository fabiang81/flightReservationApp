package com.fabiang.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabiang.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
