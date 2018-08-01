package com.natandanielapps.consensysbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.natandanielapps.consensysbooking.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.model.Employee;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "/login";
	}

	@Autowired
	EmployeeRepository employees;

	@GetMapping("/api/authenticatedUser")
    public ResponseEntity<Employee> getAuthenticatedUser(){
    	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
    	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUsername = authentication.getName();
		
		Employee employee = null;
		
		try {
		
		employee = employees.findByUsername(authenticatedUsername)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", authenticatedUsername));
		
		}catch(ResourceNotFoundException e) {
			return new ResponseEntity<Employee>(employee, headers, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
    }

}
