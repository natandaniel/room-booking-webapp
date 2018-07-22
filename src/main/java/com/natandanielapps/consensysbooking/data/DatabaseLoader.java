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

//		Employee admin1 = this.employees.save(new Employee("admin1", "admin1", "admin1", "Coke", "ROLE_MANAGER"));
//		Employee admin2 = this.employees.save(new Employee("admin2", "admin2", "admin2", "Pepsi", "ROLE_MANAGER"));
//		Employee user1 = this.employees.save(new Employee("user1", "user1", "user1", "Coke", "ROLE_USER"));
//		Employee user2 = this.employees.save(new Employee("user2", "user2", "user2", "Pepsi", "ROLE_USER"));
//
//		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin1",
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
//		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin2",
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
