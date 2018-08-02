package com.natandanielapps.consensysbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.services.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
