package com.natandanielapps.consensysbooking.repository.dummydata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.repository.RoomRepository;
import com.natandanielapps.consensysbooking.services.business.IRoomService;
import com.natandanielapps.consensysbooking.services.entities.Employee;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;

/**
 * Used for testing, loads database with two employees and 20 rooms.
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private final EmployeeRepository employees;

	public DatabaseLoader(RoomRepository rooms, MeetingRepository meetings, EmployeeRepository employees) {
		this.employees = employees;
	}

	@Autowired
	IRoomService roomService;

	@Override
	public void run(String... args) throws Exception {

		try {

			employees.findByUsername("u001")
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", "u001"));

		} catch (ResourceNotFoundException e) {

			Employee u001 = new Employee("u001", "u001", "Coke", "ROLE_MANAGER");
			employees.save(u001);

			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("u001",
					"doesn't matter", AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

			roomService.addRoom("C01");
			roomService.addRoom("C02");
			roomService.addRoom("C03");
			roomService.addRoom("C04");
			roomService.addRoom("C05");
			roomService.addRoom("C06");
			roomService.addRoom("C07");
			roomService.addRoom("C08");
			roomService.addRoom("C09");
			roomService.addRoom("C10");

			SecurityContextHolder.clearContext();
		}

		try {

			employees.findByUsername("u002")
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", "u002"));

		} catch (ResourceNotFoundException e) {

			Employee u002 = new Employee("u002", "u002", "Pepsi", "ROLE_MANAGER");
			employees.save(u002);

			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("u002",
					"doesn't matter", AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

			roomService.addRoom("P01");
			roomService.addRoom("P02");
			roomService.addRoom("P03");
			roomService.addRoom("P04");
			roomService.addRoom("P05");
			roomService.addRoom("P06");
			roomService.addRoom("P07");
			roomService.addRoom("P08");
			roomService.addRoom("P09");
			roomService.addRoom("P10");

			SecurityContextHolder.clearContext();
		}

	}
}
