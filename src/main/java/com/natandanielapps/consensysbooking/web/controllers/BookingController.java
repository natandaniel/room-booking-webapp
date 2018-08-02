package com.natandanielapps.consensysbooking.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.natandanielapps.consensysbooking.services.business.IBookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IBookingService bookingService;

	@PostMapping("/make/{meetingId}")
	public ResponseEntity<String> makeBooking(@PathVariable(value = "meetingId") String meetingId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		String message = "";
		try {
			message = bookingService.makeBooking(meetingId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("failed to execute request", headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(message, headers, HttpStatus.OK);

	}

	@PutMapping("/cancel/{meetingId}")
	public ResponseEntity<String> cancelBooking(@PathVariable(value = "meetingId") String meetingId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		String message = "";
		
		try {
			message = bookingService.cancelBooking(meetingId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("failed to execute request", headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(message, headers, HttpStatus.OK);
	}
}
