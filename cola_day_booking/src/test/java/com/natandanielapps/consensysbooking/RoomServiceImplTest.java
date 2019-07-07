package com.natandanielapps.consensysbooking;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.repository.RoomRepository;
import com.natandanielapps.consensysbooking.services.business.IRoomService;
import com.natandanielapps.consensysbooking.services.business.RoomServiceImpl;

@RunWith(SpringRunner.class)
public class RoomServiceImplTest {

	@TestConfiguration
	static class RoomServiceImplTesContextConfiguration {

		@Bean
		public IRoomService roomService() {
			return new RoomServiceImpl();
		}
	}

	@Autowired
	IRoomService roomService;

	@MockBean
	private MeetingRepository meetings;

	@MockBean
	private RoomRepository rooms;

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void whenAddRoomCalledVerified() {
		
		IRoomService roomService = Mockito.mock(IRoomService.class);
		Mockito.doNothing().when(roomService).addRoom(Mockito.any(String.class));

		roomService.addRoom("testRoomName");

		Mockito.verify(roomService, Mockito.times(1)).addRoom("testRoomName");
	}
	
	
}
