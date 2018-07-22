package com.natandanielapps.consensysbooking.service;

import com.natandanielapps.consensysbooking.model.Booking;
import com.natandanielapps.consensysbooking.repository.BookingRepository;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;

public interface IBookingService {

	Booking makeBooking(Long employeeId, Long meetingId, BookingRepository bookings, MeetingRepository meetings,
			EmployeeRepository employees);

	Booking cancelBooking(Long bookingId, BookingRepository bookings, MeetingRepository meetings);

}
