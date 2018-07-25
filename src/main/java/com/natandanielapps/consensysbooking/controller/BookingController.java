package com.natandanielapps.consensysbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.natandanielapps.consensysbooking.model.Booking;
import com.natandanielapps.consensysbooking.model.Employee;
import com.natandanielapps.consensysbooking.model.Meeting;
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

	@PostMapping("/make/{description}")
	public ResponseEntity<Booking> makeBooking(@PathVariable(value = "description") String description) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Employee employee = employees.findByLastName(currentPrincipalName);
		
		Meeting meeting = meetings.findByDescription(description);

		System.out.println("username : " + currentPrincipalName);

		Booking booking = bookingService.makeBooking(employee.getId(), meeting.getId(), bookings, meetings, employees);

		if (booking != null) {
			return new ResponseEntity<>(booking, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(headers, HttpStatus.OK);
		}
	}

	@PutMapping("/cancel/{description}")
	public ResponseEntity<Booking> cancelBooking(@PathVariable(value = "description") String description) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Employee employee = employees.findByLastName(currentPrincipalName);
		
		Meeting meeting = meetings.findByDescription(description);
		
		List<Booking> empBookings =  employee.getBookings();
		Booking bookingToCancel = null;
		
		for(Booking booking : empBookings) {
			if(booking.getMeeting() == meeting && !booking.isCancelled()) {
				bookingToCancel = booking;
			}
		}
		
		Booking cancelledBooking = null;
		
		if(bookingToCancel != null && !bookingToCancel.isCancelled()) {
			cancelledBooking = bookingService.cancelBooking(bookingToCancel.getId(), bookings, meetings);
		}

		if (cancelledBooking != null) {
			return new ResponseEntity<>(cancelledBooking, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(headers, HttpStatus.OK);
		}
	}
}
