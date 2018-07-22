package com.natandanielapps.consensysbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	Optional<List<Booking>> findByIsCancelled(boolean isCancelled);

}
