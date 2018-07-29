package com.natandanielapps.consensysbooking.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.natandanielapps.consensysbooking.model.Employee;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.repository.MeetingRepository;
import com.natandanielapps.consensysbooking.repository.RoomRepository;
import com.natandanielapps.consensysbooking.service.IRoomService;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private final RoomRepository rooms;
	
	@Autowired
	private final MeetingRepository meetings;
	
	@Autowired
	private final EmployeeRepository employees;
	
	public DatabaseLoader(RoomRepository rooms, MeetingRepository meetings, EmployeeRepository employees) {
		this.rooms = rooms;
		this.meetings = meetings;
		this.employees = employees;
	}
	
	@Autowired
	IRoomService roomService;

	@Override
	public void run(String... args) throws Exception {

//		Employee admin = this.employees.save(new Employee("admin", "admin", "admin", "ROLE_MANAGER"));
//		Employee user1 = this.employees.save(new Employee("u001", "admin", "Coke", "ROLE_MANAGER"));
//		Employee user2 = this.employees.save(new Employee("u002", "admin", "Pepsi", "ROLE_MANAGER"));
//		Employee user3 = this.employees.save(new Employee("u003", "123", "Coke", "ROLE_USER"));
//		Employee user4 = this.employees.save(new Employee("u004", "456", "Pepsi", "ROLE_USER"));
//
//		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("u001",
//				"doesn't matter", AuthorityUtils.createAuthorityList("ROLE_MANAGER")));
//		
//		roomService.addRoom("C01", rooms, meetings);
//		roomService.addRoom("C02", rooms, meetings);
//		roomService.addRoom("C03", rooms, meetings);
//		roomService.addRoom("C04", rooms, meetings);
//		roomService.addRoom("C05", rooms, meetings);
//		roomService.addRoom("C06", rooms, meetings);
//		roomService.addRoom("C07", rooms, meetings);
//		roomService.addRoom("C08", rooms, meetings);
//		roomService.addRoom("C09", rooms, meetings);
//		roomService.addRoom("C10", rooms, meetings);
//		
//		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("u002",
//				"doesn't matter", AuthorityUtils.createAuthorityList("ROLE_MANAGER")));
//		
//		roomService.addRoom("P01", rooms, meetings);
//		roomService.addRoom("P02", rooms, meetings);
//		roomService.addRoom("P03", rooms, meetings);
//		roomService.addRoom("P04", rooms, meetings);
//		roomService.addRoom("P05", rooms, meetings);
//		roomService.addRoom("P06", rooms, meetings);
//		roomService.addRoom("P07", rooms, meetings);
//		roomService.addRoom("P08", rooms, meetings);
//		roomService.addRoom("P09", rooms, meetings);
//		roomService.addRoom("P10", rooms, meetings);
//		
//		SecurityContextHolder.clearContext();
	}
}
