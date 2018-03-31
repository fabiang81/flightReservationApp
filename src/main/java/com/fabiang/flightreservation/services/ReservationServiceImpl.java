package com.fabiang.flightreservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabiang.flightreservation.dto.ReservationRequest;
import com.fabiang.flightreservation.entities.Flight;
import com.fabiang.flightreservation.entities.Passenger;
import com.fabiang.flightreservation.entities.Reservation;
import com.fabiang.flightreservation.repos.FlightRepository;
import com.fabiang.flightreservation.repos.PassengerRepository;
import com.fabiang.flightreservation.repos.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;

	@Override
	public Reservation bookFlight(ReservationRequest request) {
		//MakePayment impl
		
		Long flightId = request.getFlightId();
		Flight flight = flightRepository.findOne(flightId);
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getFirstName());
		passenger.setLastName(request.getLastName());
		passenger.setPhone(request.getPhone());
		passenger.setEmail(request.getEmail());
		
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		
		Reservation savedReservation = reservationRepository.save(reservation);
		
		return savedReservation;
	}

}
