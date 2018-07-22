package com.natandanielapps.consensysbooking.service;

import com.natandanielapps.consensysbooking.model.Room;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.repository.RoomRepository;

public interface IRoomService {

	Room addRoom(String roomName, RoomRepository rooms, MeetingRepository meetings);
}
