package com.natandanielapps.consensysbooking.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.natandanielapps.consensysbooking.services.business.IBookingService;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.web.dto.BookingDTO;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	IBookingService bookingService;

	@PostMapping("/make/{meetingId}")
	public ResponseEntity<BookingDTO> makeBooking(@PathVariable(value = "meetingId") String meetingId)
			throws ResourceNotFoundException, RestClientException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		return new ResponseEntity<BookingDTO>(bookingService.makeBooking(meetingId), headers, HttpStatus.OK);

	}

	@PutMapping("/cancel/{meetingId}")
	public ResponseEntity<BookingDTO> cancelBooking(@PathVariable(value = "meetingId") String meetingId)
			throws ResourceNotFoundException, RestClientException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		return new ResponseEntity<BookingDTO>(bookingService.cancelBooking(meetingId), headers, HttpStatus.OK);

	}
}
