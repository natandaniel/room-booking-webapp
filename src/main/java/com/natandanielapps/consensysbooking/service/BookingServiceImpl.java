package com.natandanielapps.consensysbooking.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.natandanielapps.consensysbooking.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.model.Booking;
import com.natandanielapps.consensysbooking.model.Employee;
import com.natandanielapps.consensysbooking.model.Meeting;
import com.natandanielapps.consensysbooking.repository.BookingRepository;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.rest.RestTemplateFactory;

@Service
public class BookingServiceImpl implements IBookingService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RestTemplateFactory restTemplateFactory;

	@Autowired
	BookingRepository bookings;

	@Autowired
	MeetingRepository meetings;

	@Autowired
	EmployeeRepository employees;

	@Override
	public String makeBooking(String meetingId) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUserName = authentication.getName();

		log.info("user : " + authenticatedUserName + " is authenticated");
		log.info("meetingId : " + meetingId);

		Meeting meeting = meetings.findById(Long.valueOf(meetingId))
				.orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", meetingId));

		log.info("fetching employee...");
		Employee employee = employees.findByUsername(authenticatedUserName)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", authenticatedUserName));

		if (meeting.isMeetingBookable()) {

			log.info("meeting is bookable");

			Booking booking = new Booking(employee, meeting);
			bookings.save(booking);

			log.info("booking made");

			meeting.setMeetingBookable(false);
			meeting.setMeetingBooked(true);
			meeting.setCurrentUsername(employee.getUsername());

			RestTemplate restTemplate = restTemplateFactory.getObject();
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
			String url = "http://localhost:8080/api/meetings/" + meetingId;
			HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
			HttpEntity<Meeting> requestUpdate = new HttpEntity<>(meeting, httpHeaders);

			log.info("updating meeting...");

			try {
				restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Void.class);

			} catch (Exception e) {
				log.error(e.getMessage());
				throw new Exception("Failed to update meeting");
			}

			log.info("meeting updated");

			return "booking made";

		} else {
			return "meeting already booked";
		}
	}

	@Override
	public String cancelBooking(String meetingId) throws Exception {

		Booking bookingToCancel = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUserName = authentication.getName();
		
		log.info("user : " + authenticatedUserName + " is authenticated");
		log.info("meeting id : " + meetingId);

		Meeting meeting = meetings.findById(Long.valueOf(meetingId))
				.orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", meetingId));
		
		Employee employee = employees.findByUsername(authenticatedUserName)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", authenticatedUserName));

		List<Booking> employeeBookings = employee.getBookings();

		for (Booking booking : employeeBookings) {
			if (booking.getMeeting() == meeting && !booking.isCancelled()) {
				bookingToCancel = booking;
				break;
			}
		}

		if (bookingToCancel != null && !bookingToCancel.isCancelled()) {

			Long bookingToCancelId = bookingToCancel.getId();

			Booking booking = bookings.findById(bookingToCancel.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingToCancelId));

			booking.setCancelled(true);
			bookings.save(booking);

			meeting.setMeetingBookable(true);
			meeting.setMeetingBooked(false);
			meeting.setCurrentUsername(null);
			
			RestTemplate restTemplate = restTemplateFactory.getObject();
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
			String url = "http://localhost:8080/api/meetings/" + meetingId;
			HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
			HttpEntity<Meeting> requestUpdate = new HttpEntity<>(meeting, httpHeaders);

			try {
				restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Void.class);

			} catch (Exception e) {
				log.error(e.getMessage());
				throw new Exception("Failed to update meeting");
			}

			return "booking cancelled";

		} else {
			return "no booking to cancel";
		}
	}
}
