package com.natandanielapps.consensysbooking.web.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;

/**
 * API exception handler.
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles {@link ResourceNotFoundException} 
	 * @param e handled exception
	 * @return instance of {@link ApiError} with error message
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException e) {
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Resource not found", e));
	}

	/**
	 * Handles {@link RestClientException} 
	 * @param e handled exception
	 * @return instance of {@link ApiError} with error message
	 */
	@ExceptionHandler(RestClientException.class)
	protected ResponseEntity<Object> handleRestClientException(RestClientException e) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Failed request", e));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(apiError, headers, apiError.getStatus());
	}

}
