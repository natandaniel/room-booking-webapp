package com.natandanielapps.consensysbooking.web.dto;

import lombok.Data;

/**
 * Data transfer object for {@link Booking} entities.
 *
 */
@Data
public class BookingDTO {

	private Long bookingId;
	private String employeeUsername;
	private Long meetingId;
	private String status;
	private String errorMessage;

	/**
	 * Used when a booking cannot be made or when a booking cannot be cancelled.
	 * 
	 * @param errorMessage
	 *            an error message
	 */
	public BookingDTO(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Used to build an instance of {@link BookingDTO} from details of an
	 * {@link Booking} entity.
	 * 
	 * @param bookingId booking identifier 
	 * @param employeeUsername author of booking
	 * @param meetingId identifier of meeting being booked
	 * @param status 'CREATED' if booking was made, 'CANCELLED' if booking was cancelled
	 */
	public BookingDTO(Long bookingId, String employeeUsername, Long meetingId, String status) {
		this.bookingId = bookingId;
		this.employeeUsername = employeeUsername;
		this.meetingId = meetingId;
		this.status = status;
	}
}
