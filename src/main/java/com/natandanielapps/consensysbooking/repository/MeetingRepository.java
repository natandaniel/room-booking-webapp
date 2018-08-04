package com.natandanielapps.consensysbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.services.entities.Meeting;

/**
 * Repository for {@link Meeting} entities
 * @author Natan
 *
 */
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
