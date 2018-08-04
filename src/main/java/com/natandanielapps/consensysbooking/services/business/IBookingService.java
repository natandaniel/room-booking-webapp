package com.natandanielapps.consensysbooking.services.business;

import org.springframework.web.client.RestClientException;

import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;
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
	 * @throws Exception
	 */
	BookingDTO makeBooking(String meetingId) throws ResourceNotFoundException, RestClientException;

	/**
	 * Cancels a booking made for a meeting.
	 * 
	 * @param meetingId
	 *            a {@link Meeting} identifier
	 * @return a {@link BookingDTO} containing the details of the booking cancelled
	 * @throws Exception
	 */
	BookingDTO cancelBooking(String meetingId) throws ResourceNotFoundException, RestClientException;

}
