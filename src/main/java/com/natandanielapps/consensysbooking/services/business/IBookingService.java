package com.natandanielapps.consensysbooking.services.business;

public interface IBookingService {

	String makeBooking(String description) throws Exception;

	String cancelBooking(String description) throws Exception;

}
