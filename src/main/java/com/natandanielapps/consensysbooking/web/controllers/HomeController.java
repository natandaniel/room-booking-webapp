package com.natandanielapps.consensysbooking.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.services.entities.Employee;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;

/**
 * Handles requests to login and get authenticated user details.
 *
 */
@Controller
public class HomeController {

	@Autowired
	EmployeeRepository employees;

	/**
	 * Serves the home page.
	 * @return home page view
	 */
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}

	/**
	 * Serves the login page.
	 * @return login page view
	 */
	@GetMapping("/login")
	public String login() {
		return "/login";
	}

	/**
	 * Gets the authenticated user's details.
	 * @return instance of {@link Employee} containing the authenticated user's details
	 * @throws ResourceNotFoundException exception thrown if user is not registered in system
	 */
	@GetMapping("/api/authenticatedUser")
	public ResponseEntity<Employee> getAuthenticatedUser() throws ResourceNotFoundException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUsername = authentication.getName();

		Employee employee = employees.findByUsername(authenticatedUsername)
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", authenticatedUsername));

		return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
	}

}
