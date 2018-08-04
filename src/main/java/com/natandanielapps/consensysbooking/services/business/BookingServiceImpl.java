package com.natandanielapps.consensysbooking.services.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.natandanielapps.consensysbooking.repository.BookingRepository;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.services.entities.Booking;
import com.natandanielapps.consensysbooking.services.entities.Employee;
import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.services.infrastructure.MeetingRepoRestClient;
import com.natandanielapps.consensysbooking.services.infrastructure.tools.RestTemplateFactory;
import com.natandanielapps.consensysbooking.web.dto.BookingDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingServiceImpl implements IBookingService {

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
	public BookingDTO makeBooking(String meetingId) throws ResourceNotFoundException, RestClientException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUserName = authentication.getName();

		log.info("makeBooking - meetingId : " + meetingId + " - authenticated user : " + authenticatedUserName);

		log.info("getting meeting ...");
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

			log.info("booking made for employee " + employee.getUsername());
			log.info("updating meeting details ...");

			meeting.setMeetingBookable(false);
			meeting.setMeetingBooked(true);
			meeting.setCurrentUsername(employee.getUsername());

			meetingRepoRestClient.updateMeeting(meetingId, meeting);

			return new BookingDTO(booking.getId(), employee.getUsername(), meeting.getId(), "CREATED");

		} else {
			return new BookingDTO(
					"Meeting is already booked by another employee. Cannot book meeting for " + employee.getUsername());
		}
	}

	@Override
	public BookingDTO cancelBooking(String meetingId) throws ResourceNotFoundException, RestClientException {

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

			meetingRepoRestClient.updateMeeting(meetingId, meeting);

			return new BookingDTO(bookingToCancel.getId(), employee.getUsername(), meeting.getId(), "CANCELLED");

		} else {
			return new BookingDTO("No active booking was found for this meeting - no booking was cancelled");
		}
	}
}
