package com.natandanielapps.consensysbooking.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.model.Meeting;

@Repository
public interface MeetingRepository extends PagingAndSortingRepository<Meeting, Long> {

	Optional<Meeting> findByDescription(String description);
}
