package com.natandanielapps.consensysbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.model.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
