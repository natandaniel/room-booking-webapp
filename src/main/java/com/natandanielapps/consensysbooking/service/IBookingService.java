package com.natandanielapps.consensysbooking.service;

public interface IBookingService {

	String makeBooking(String description) throws Exception;

	String cancelBooking(String description) throws Exception;

}
