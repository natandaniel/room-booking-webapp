package com.natandanielapps.consensysbooking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.services.entities.Booking;

/**
 * Repository for {@link Booking} entities.
 *
 */
@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

}
