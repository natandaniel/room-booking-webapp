package com.natandanielapps.consensysbooking.services.business;

import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.web.dto.BookingDTO;

/**
 * Service to make and cancel bookings for a meeting.
 *
 */
public interface IBookingService {

	/**
	 * Makes a booking for a meeting.
	 * 
	 * @param meetingId
	 *            a {@link Meeting} identifier
	 * @return a {@link BookingDTO} containing the details of the booking made
	 */
	BookingDTO makeBooking(String meetingId);

	/**
	 * Cancels a booking made for a meeting.
	 * 
	 * @param meetingId
	 *            a {@link Meeting} identifier
	 * @return a {@link BookingDTO} containing the details of the booking cancelled
	 */
	BookingDTO cancelBooking(String meetingId);
}
