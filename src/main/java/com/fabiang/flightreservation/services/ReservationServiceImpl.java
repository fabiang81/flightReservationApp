package com.fabiang.flightreservation.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabiang.flightreservation.dto.ReservationRequest;
import com.fabiang.flightreservation.entities.Flight;
import com.fabiang.flightreservation.entities.Passenger;
import com.fabiang.flightreservation.entities.Reservation;
import com.fabiang.flightreservation.repos.FlightRepository;
import com.fabiang.flightreservation.repos.PassengerRepository;
import com.fabiang.flightreservation.repos.ReservationRepository;
import com.fabiang.flightreservation.util.EmailUtil;
import com.fabiang.flightreservation.util.PdfGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Value("${com.fabian81.flightreservation.itinerary.dirpath}")
	private String ITINERARY_DIR;

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	PdfGenerator pdfGenerator;
	
	@Autowired
	EmailUtil emailUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Override
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {
		//MakePayment impl
		
		LOGGER.info("Inside bookFlight()");
		
		Long flightId = request.getFlightId();
		LOGGER.info("Fetchin Flight for flight id: "+flightId);
		
		Flight flight = flightRepository.findOne(flightId);
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getFirstName());
		passenger.setLastName(request.getLastName());
		passenger.setPhone(request.getPhone());
		passenger.setEmail(request.getEmail());
		LOGGER.info("Saving the passenger: "+passenger);
		
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		
		LOGGER.info("Saving the reservation: "+reservation);
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String filePath = ITINERARY_DIR+savedReservation.getId()+".pdf";
		LOGGER.info("Generating the itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath);
		
		LOGGER.info("Sending the itinerary");
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		
		return savedReservation;
	}

}
