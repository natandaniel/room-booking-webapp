package com.natandanielapps.consensysbooking.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.model.Meeting;

@Repository
public interface MeetingRepository extends PagingAndSortingRepository<Meeting, Long> {

	Meeting findByDescription(String description);
}
