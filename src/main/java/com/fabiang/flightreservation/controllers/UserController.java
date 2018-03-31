package com.fabiang.flightreservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fabiang.flightreservation.entities.Flight;
import com.fabiang.flightreservation.entities.User;
import com.fabiang.flightreservation.repos.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping("/showRegistration")
	public String showRegistrationPage(@ModelAttribute("user") User user) {
		return "login/registerUser";
	}
	
	@RequestMapping(value="/registerUser", method=RequestMethod.POST)
	public String register(@ModelAttribute("user") User user, ModelMap modelMap) {
		userRepository.save(user);
		modelMap.addAttribute("msg", "User succesfully registered");
		return "login/registerUser";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@ModelAttribute("user") User user) {
		return "login/login";
	}
	
	@RequestMapping(value="/validateLogin", method=RequestMethod.POST)
	public String validateLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			@ModelAttribute("user") User user, @ModelAttribute("flight") Flight flight, ModelMap modelMap) {
		User foundUser = userRepository.findByEmail(email);
		if(foundUser.getPassword().equals(password)) {
			return "findFlights";
		}else {
			modelMap.addAttribute("msg", "Invalid username or password. Please try again");
			return "login/login";
		}
	}

}
