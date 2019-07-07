package com.natandanielapps.consensysbooking.web.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * API error container used to display user friendly error messages to front
 * end.
 *
 */
@Data
public class ApiError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;

	/**
	 * Default constructor, fills in time of error.
	 */
	public ApiError() {
		timestamp = LocalDateTime.now();
	}
	
	/**
	 * Fills in error details.
	 * @param status response HTTP status code
	 * @param message error message
	 * @param ex contained exception
	 */
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
}
