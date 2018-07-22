package com.natandanielapps.consensysbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.natandanielapps.consensysbooking.model.Booking;
import com.natandanielapps.consensysbooking.repository.BookingRepository;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.service.IBookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	BookingRepository bookings;

	@Autowired
	MeetingRepository meetings;

	@Autowired
	EmployeeRepository employees;

	@Autowired
	IBookingService bookingService;

	@PostMapping("/make/{employeeId}/{meetingId}")
	public ResponseEntity<Booking> makeBooking(@PathVariable(value = "employeeId") Long employeeId,
			@PathVariable(value = "meetingId") Long meetingId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		Booking booking = bookingService.makeBooking(employeeId, meetingId, bookings, meetings, employees);

		if (booking != null) {
			return new ResponseEntity<>(booking, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(headers, HttpStatus.OK);
		}
	}

	@PutMapping("/cancel/{id}")
	public ResponseEntity<Booking> cancelBooking(@PathVariable(value = "id") Long bookingId) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		Booking cancelledBooking = bookingService.cancelBooking(bookingId, bookings, meetings);

		if (cancelledBooking != null) {
			return new ResponseEntity<>(cancelledBooking, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(headers, HttpStatus.OK);
		}
	}
}
