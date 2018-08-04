package com.natandanielapps.consensysbooking.services.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.repository.RoomRepository;
import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.services.entities.Room;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;

@Service
public class RoomServiceImpl implements IRoomService {

	@Autowired
	RoomRepository rooms;

	@Autowired
	MeetingRepository meetings;

	@Override
	public void addRoom(String roomName) {

		try {

			rooms.findByRoomName(roomName)
					.orElseThrow(() -> new ResourceNotFoundException("Room", "roomName", roomName));

		} catch (ResourceNotFoundException e) {

			Room room = rooms.save(new Room(roomName, new ArrayList<Meeting>()));

			for (int i = 0; i < 10; i++) {

				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 8 + i);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				Date d = cal.getTime();

				Meeting meeting = new Meeting(d, true, false, room);
				meetings.save(meeting);
			}
		}
	}
}
