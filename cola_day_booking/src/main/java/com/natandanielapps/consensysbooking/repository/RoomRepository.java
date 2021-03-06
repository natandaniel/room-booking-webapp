package com.natandanielapps.consensysbooking.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.natandanielapps.consensysbooking.services.entities.Room;

/**
 * Repository for {@link Room} entities.
 * @author Natan
 *
 */
@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
	
	@SuppressWarnings("unchecked")
	@Override
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	Room save(@Param("room") Room room);
	
	Optional<Room> findByRoomName(String roomName);

}
