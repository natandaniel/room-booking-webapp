package com.natandanielapps.consensysbooking;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.natandanielapps.consensysbooking.repository.RoomRepository;
import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.services.entities.Room;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RoomRepository rooms;

	@Test
	public void whenFindByRoomName_thenReturnRoom() {

		Room test = new Room("test", new ArrayList<Meeting>());
		entityManager.persist(test);
		entityManager.flush();

		Optional<Room> found = rooms.findByRoomName(test.getRoomName());

		// then
		assertEquals(found.get().getRoomName(), test.getRoomName());
	}
}
