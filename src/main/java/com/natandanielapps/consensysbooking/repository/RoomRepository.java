package com.natandanielapps.consensysbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	
	@SuppressWarnings("unchecked")
	@Override
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	Room save(@Param("room") Room room);

}
