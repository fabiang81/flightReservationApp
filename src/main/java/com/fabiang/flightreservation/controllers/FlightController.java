package com.fabiang.flightreservation.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fabiang.flightreservation.entities.Flight;
import com.fabiang.flightreservation.repos.FlightRepository;


@Controller
public class FlightController {
	
	@Autowired
	FlightRepository flightRepository;
	
	@RequestMapping("findFlights")
	public String findFlights(@RequestParam("departureCity") String from, @RequestParam("arrivalCity") String to, 
			@RequestParam("dateOfDeparture") String departureDate, ModelMap modelMap) {

		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = null;
		try {
			date = formatter2.parse(departureDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Flight> flights = flightRepository.findFlights(from, to, date);
		modelMap.addAttribute("flights", flights);
		return "displayFlights";
	}

}
