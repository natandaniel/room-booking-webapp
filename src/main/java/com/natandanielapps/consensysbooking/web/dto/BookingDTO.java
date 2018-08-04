package com.natandanielapps.consensysbooking.web.dto;

import lombok.Data;

/**
 * Data tranfer object for {@link Booking} entities.
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
	 * @param bookingId
	 * @param employeeUsername
	 * @param meetingId
	 * @param isCancelled
	 */
	public BookingDTO(Long bookingId, String employeeUsername, Long meetingId, String status) {
		this.bookingId = bookingId;
		this.employeeUsername = employeeUsername;
		this.meetingId = meetingId;
		this.status = status;
	}
}
