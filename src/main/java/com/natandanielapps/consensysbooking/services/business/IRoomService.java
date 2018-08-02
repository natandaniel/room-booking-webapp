package com.natandanielapps.consensysbooking.services.business;

import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.repository.RoomRepository;
import com.natandanielapps.consensysbooking.services.entities.Room;

public interface IRoomService {

	Room addRoom(String roomName, RoomRepository rooms, MeetingRepository meetings);
}
