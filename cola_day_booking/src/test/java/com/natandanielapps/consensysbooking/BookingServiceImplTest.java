package com.natandanielapps.consensysbooking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;

import com.natandanielapps.consensysbooking.repository.BookingRepository;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.services.business.BookingServiceImpl;
import com.natandanielapps.consensysbooking.services.business.IBookingService;
import com.natandanielapps.consensysbooking.services.entities.Booking;
import com.natandanielapps.consensysbooking.services.entities.Employee;
import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.services.entities.Room;
import com.natandanielapps.consensysbooking.services.infrastructure.MeetingRepoRestClient;
import com.natandanielapps.consensysbooking.web.dto.BookingDTO;

@RunWith(SpringRunner.class)
public class BookingServiceImplTest {

	@TestConfiguration
	static class BookingServiceImplTesContextConfiguration {

		@Bean
		public IBookingService bookingService() {
			return new BookingServiceImpl();
		}
	}

	@Autowired
	IBookingService bookingService;

	@MockBean
	private BookingRepository bookings;

	@MockBean
	private MeetingRepository meetings;

	@MockBean
	private EmployeeRepository employees;

	@MockBean
	private MeetingRepoRestClient meetingRepoRestClient;

	@Before
	public void setUp() {

		User testUser = new User("test", "test", AuthorityUtils.createAuthorityList("ROLE_USER"));

		Authentication auth = new UsernamePasswordAuthenticationToken(testUser, null);

		SecurityContextHolder.getContext().setAuthentication(auth);

		Employee testEmployee = new Employee("test", "test", "test", "ROLE_USER");

		Mockito.when(employees.findByUsername(testEmployee.getUsername())).thenReturn(Optional.of(testEmployee));

		Meeting testMeeting = new Meeting(new Date(), true, false, new Room());
		testMeeting.setId(1L);
		
		Meeting testMeeting2 = new Meeting(new Date(), false, true, new Room());
		testMeeting2.setId(2L);

		Mockito.when(meetings.findById(1L)).thenReturn(Optional.of(testMeeting));
		Mockito.when(meetings.findById(2L)).thenReturn(Optional.of(testMeeting2));

		Booking testBooking = new Booking(testEmployee, testMeeting2);
		
		List<Booking> bookingsList = new ArrayList<Booking>();
		bookingsList.add(testBooking);
		testEmployee.setBookings(bookingsList);

		Mockito.when(bookings.save(Mockito.any(Booking.class))).thenReturn(testBooking);

		Mockito.doNothing().when(meetingRepoRestClient).updateMeeting(Mockito.any(String.class),
				Mockito.any(Meeting.class));
	}

	@Test
	public void whenMeetingBookable_thenBookingShouldBeMade() {

		BookingDTO bookingDTO = bookingService.makeBooking("1");

		assertEquals("test", bookingDTO.getEmployeeUsername());
		assertEquals("CREATED", bookingDTO.getStatus());
		assertEquals(Long.valueOf("1"), bookingDTO.getMeetingId());
	}
	
	@Test
	public void whenMeetingNotBookable_thenBookingShouldNotBeMade() {

		BookingDTO bookingDTO = bookingService.makeBooking("2");

		assertNotNull(bookingDTO.getErrorMessage());
	}
	
	@Test
	public void whenMeetingBooked_thenBookingShouldBeCancelled() {

		BookingDTO bookingDTO = bookingService.cancelBooking("2");

		assertEquals("test", bookingDTO.getEmployeeUsername());
		assertEquals("CANCELLED", bookingDTO.getStatus());
		assertEquals(Long.valueOf("2"), bookingDTO.getMeetingId());
	}
	
	@Test
	public void whenMeetingBookedByOther_thenBookingShouldNotBeCancelled() {

		BookingDTO bookingDTO = bookingService.cancelBooking("1");

		assertNotNull(bookingDTO.getErrorMessage());
	}
}
