package com.natandanielapps.consensysbooking.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.model.Room;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
	
	@SuppressWarnings("unchecked")
	@Override
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	Room save(@Param("room") Room room);

}
