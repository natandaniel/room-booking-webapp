package com.natandanielapps.consensysbooking.services.business;

import com.natandanielapps.consensysbooking.services.entities.Room;

/**
 * Service to manage rooms.
 *
 */
public interface IRoomService {

	/**
	 * Adds a room in the system.
	 * @param roomName room name
	 * @return added {@link Room}
	 */
	void addRoom(String roomName);
}
