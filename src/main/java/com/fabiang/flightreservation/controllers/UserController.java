package com.fabiang.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fabiang.flightreservation.entities.Flight;
import com.fabiang.flightreservation.entities.User;
import com.fabiang.flightreservation.repos.UserRepository;
import com.fabiang.flightreservation.services.SecurityService;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SecurityService securityService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping("/showRegistration")
	public String showRegistrationPage(@ModelAttribute("user") User user) {
		LOGGER.info("Inside showRegistrationPage()");
		return "login/registerUser";
	}
	
	@RequestMapping(value="/registerUser", method=RequestMethod.POST)
	public String register(@ModelAttribute("user") User user, ModelMap modelMap) {
		LOGGER.info("Inside register()"+user);
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		modelMap.addAttribute("msg", "User succesfully registered");
		return "login/registerUser";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@ModelAttribute("user") User user) {
		LOGGER.info("Inside login()"+user);
		return "login/login";
	}
	
	@RequestMapping(value="/validateLogin", method=RequestMethod.POST)
	public String validateLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			@ModelAttribute("user") User user, @ModelAttribute("flight") Flight flight, ModelMap modelMap) {
		LOGGER.info("Inside validateLogin()"+email);
		boolean loginResponse = securityService.login(email, password);
		if(loginResponse) {
			return "findFlights";
		}else {
			modelMap.addAttribute("msg", "Invalid username or password. Please try again");
			return "login/login";
		}
	}

}
