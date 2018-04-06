package com.fabiang.flightreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlightreservationApplication {//extends SpringBootServletInitializer
	
	//configuration for deploying a war file
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(FlightreservationApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(FlightreservationApplication.class, args);
	}
}
