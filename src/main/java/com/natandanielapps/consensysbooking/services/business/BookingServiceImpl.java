package com.natandanielapps.consensysbooking.services.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.natandanielapps.consensysbooking.repository.BookingRepository;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.services.entities.Booking;
import com.natandanielapps.consensysbooking.services.entities.Employee;
import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.services.infrastructure.MeetingRepoRestClient;
import com.natandanielapps.consensysbooking.services.infrastructure.tools.RestTemplateFactory;

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

	@Autowired
	MeetingRepoRestClient meetingRepoRestClient;

	@Override
	public String makeBooking(String meetingId) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUserName = authentication.getName();

		log.info("makeBooking - meetingId : " + meetingId + " employee : " + authenticatedUserName);

		log.info("getting meeting...");
		Meeting meeting = meetings.findById(Long.valueOf(meetingId))
				.orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", meetingId));

		log.info("getting employee...");
		Employee employee = employees.findByUsername(authenticatedUserName)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", authenticatedUserName));

		if (meeting.isMeetingBookable()) {

			log.info("meeting is bookable");
			log.info("making booking ...");

			Booking booking = new Booking(employee, meeting);
			bookings.save(booking);

			log.info("booking made");
			log.info("updating meeting details...");

			meeting.setMeetingBookable(false);
			meeting.setMeetingBooked(true);
			meeting.setCurrentUsername(employee.getUsername());

			ResponseEntity<Meeting> updatedMeetingEntity = meetingRepoRestClient.updateMeeting(meetingId, meeting);

			return "booking made";

		} else {
			return "meeting already booked";
		}
	}

	@Override
	public String cancelBooking(String meetingId) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUserName = authentication.getName();

		log.info("cancelBooking - meetingId : " + meetingId + " employee : " + authenticatedUserName);

		Booking bookingToCancel = null;

		log.info("getting meeting...");
		Meeting meeting = meetings.findById(Long.valueOf(meetingId))
				.orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", meetingId));

		log.info("getting employee...");
		Employee employee = employees.findByUsername(authenticatedUserName)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", authenticatedUserName));

		log.info("getting employee's bookings record...");
		List<Booking> employeeBookings = employee.getBookings();

		log.info("checking if employee has an active booking for this meeting ...");
		for (Booking booking : employeeBookings) {
			if (booking.getMeeting() == meeting && !booking.isCancelled()) {
				log.info("active booking found");
				bookingToCancel = booking;
				break;
			}
		}

		if (bookingToCancel != null && !bookingToCancel.isCancelled()) {

			log.info("cancelling booking...");
			bookingToCancel.setCancelled(true);
			bookings.save(bookingToCancel);

			log.info("booking cancelled");
			log.info("updating meeting details...");

			meeting.setMeetingBookable(true);
			meeting.setMeetingBooked(false);
			meeting.setCurrentUsername(null);

			ResponseEntity<Meeting> updatedMeetingEntity = meetingRepoRestClient.updateMeeting(meetingId, meeting);

			return "booking cancelled";

		} else {
			log.info("no active booking found - no booking to cancel");
			return "no booking to cancel";
		}
	}
}
