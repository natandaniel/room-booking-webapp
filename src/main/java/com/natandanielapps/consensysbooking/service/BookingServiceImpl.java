package com.natandanielapps.consensysbooking.service;

import org.springframework.stereotype.Service;

import com.natandanielapps.consensysbooking.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.model.Booking;
import com.natandanielapps.consensysbooking.model.Employee;
import com.natandanielapps.consensysbooking.model.Meeting;
import com.natandanielapps.consensysbooking.repository.BookingRepository;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;

@Service
public class BookingServiceImpl implements IBookingService {

	@Override
	public Booking makeBooking(Long employeeId, Long meetingId, BookingRepository bookings, MeetingRepository meetings,
			EmployeeRepository employees) {

		Employee employee = employees.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

		Meeting meeting = meetings.findById(meetingId)
				.orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", meetingId));

		if (meeting.isMeetingBookable()) {

			Booking booking = new Booking(employee, meeting);
			Booking savedBooking = bookings.save(booking);

			meeting.setMeetingBookable(false);
			meeting.setMeetingBooked(true);
			meetings.save(meeting);

			return savedBooking;
		} else {
			return null;
		}
	}

	@Override
	public Booking cancelBooking(Long bookingId, BookingRepository bookings, MeetingRepository meetings) {

		Booking booking = bookings.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

		if (booking.isCancelled()) {
			return booking;
		} else {
			booking.setCancelled(true);

			Meeting meeting = meetings.findById(booking.getMeeting().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", booking.getMeeting().getId()));

			meeting.setMeetingBookable(true);
			meeting.setMeetingBooked(false);
			meetings.save(meeting);

			return bookings.save(booking);
		}
	}
}
