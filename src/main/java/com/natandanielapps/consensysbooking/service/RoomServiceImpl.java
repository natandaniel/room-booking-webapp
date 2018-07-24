package com.natandanielapps.consensysbooking.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.natandanielapps.consensysbooking.model.Meeting;
import com.natandanielapps.consensysbooking.model.Room;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.repository.RoomRepository;

@Service
public class RoomServiceImpl implements IRoomService {

	@Override
	public Room addRoom(String roomName, RoomRepository rooms, MeetingRepository meetings) {

		Room room = rooms.save(new Room(roomName, new ArrayList<Meeting>()));

		for (int i = 0; i < 10; i++) {

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 8 + i);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			Date d = cal.getTime();

			Meeting meeting = new Meeting(roomName + d.toString(),d, true, false, room);
			meetings.save(meeting);
		}

		return room;
	}
}
