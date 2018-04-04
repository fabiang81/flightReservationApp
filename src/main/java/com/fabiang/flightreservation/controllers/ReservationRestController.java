package com.fabiang.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabiang.flightreservation.dto.ReservationUpdateRequest;
import com.fabiang.flightreservation.entities.Reservation;
import com.fabiang.flightreservation.repos.ReservationRepository;

@RestController
public class ReservationRestController {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRestController.class);
	
	@RequestMapping("/reservations/{id}")
	public Reservation findReservation(@PathVariable("id") Long id) {
		LOGGER.info("Inside findReservation() for id: "+id);
		return reservationRepository.findOne(id);
	}
	
	@RequestMapping("/reservations/")
	public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
		LOGGER.info("Inside updateReservation() for: "+request);
		Reservation reservation = reservationRepository.findOne(request.getId());
		reservation.setNumberOfBags(request.getNumberOfBags());
		reservation.setCheckedIn(request.getCheckedIn());
		LOGGER.info("Saving reservations");
		return reservationRepository.save(reservation);
	}

}
