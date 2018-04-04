package com.fabiang.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fabiang.flightreservation.dto.ReservationRequest;
import com.fabiang.flightreservation.entities.Flight;
import com.fabiang.flightreservation.entities.Reservation;
import com.fabiang.flightreservation.repos.FlightRepository;
import com.fabiang.flightreservation.services.ReservationService;

@Controller
public class ReservationController {
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	ReservationService reservationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);
	
	@RequestMapping("/showCompleteReservation")
	public String c(@RequestParam("id") Long flightId, 
			@ModelAttribute("reservationRequest") ReservationRequest reservationR, ModelMap modelMap) {
		LOGGER.info("Into showCompleteReservation() invoked with the flightId: "+flightId);
		Flight flight = flightRepository.findOne(flightId);
		modelMap.addAttribute("flight", flight);
		return "completeReservation";
	}
	
	@RequestMapping(value="/completeReservation", method=RequestMethod.POST)
	public String completeReservation(ReservationRequest request, ModelMap modelMap) {
		LOGGER.info("Into completeReservation() invoked with the ReservationRequest: "+request);
		Reservation reservation = reservationService.bookFlight(request);
		modelMap.addAttribute("msg", "Reservation created succesfully with id: "+reservation.getId());
		return "reservationConfirmation";
	}

}
