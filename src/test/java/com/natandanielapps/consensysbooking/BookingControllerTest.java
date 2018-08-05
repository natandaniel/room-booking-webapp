package com.natandanielapps.consensysbooking;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natandanielapps.consensysbooking.services.business.IBookingService;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.web.controllers.BookingController;
import com.natandanielapps.consensysbooking.web.dto.BookingDTO;
import com.natandanielapps.consensysbooking.web.exceptions.RestExceptionHandler;

@RunWith(SpringRunner.class)
public class BookingControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private IBookingService bookingService;

	@InjectMocks
	private BookingController bookingController;

	private JacksonTester<BookingDTO> jsonBookingDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		JacksonTester.initFields(this, new ObjectMapper());

		mockMvc = MockMvcBuilders.standaloneSetup(bookingController).setControllerAdvice(new RestExceptionHandler())
				.build();
	}

	@Test
	public void whenMakeBooking_ShouldReturnHttpStatusCREATED() throws Exception {

		BookingDTO bookingDTO = new BookingDTO(1L, "testUsername", 1l, "CREATED");

		Mockito.when(bookingService.makeBooking(Mockito.any(String.class))).thenReturn(bookingDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/bookings/make/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		// when
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(jsonBookingDTO.write(bookingDTO).getJson(), response.getContentAsString());
	}

	@Test
	public void whenMakeBooking_ShouldThrowHttpStatusNotFound() throws Exception {

		Mockito.when(bookingService.makeBooking(Mockito.any(String.class)))
				.thenThrow(new ResourceNotFoundException("Meeting", "id", 1L));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/bookings/make/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		// when
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	@Test
	public void whenCancelBooking_ShouldReturnHttpStatusOK() throws Exception {

		BookingDTO bookingDTO = new BookingDTO(1L, "testUsername", 1l, "CANCELLED");

		Mockito.when(bookingService.cancelBooking(Mockito.any(String.class))).thenReturn(bookingDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/bookings/cancel/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		// when
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonBookingDTO.write(bookingDTO).getJson(), response.getContentAsString());
	}
}
