package com.natandanielapps.consensysbooking.repository.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.services.entities.Employee;
import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.services.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.web.websockets.WebSocketConfiguration;

@Component
@RepositoryEventHandler(Meeting.class)
public class MeetingEventHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;
	
	@Autowired
	EmployeeRepository employees;

	@Autowired
	public MeetingEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	@HandleAfterSave
	public void updateMeeting(Meeting meeting) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUserName = authentication.getName();
		
		Employee employee = employees.findByUsername(authenticatedUserName)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", authenticatedUserName));
		
		this.websocket.convertAndSend(WebSocketConfiguration.MESSAGE_PREFIX + "/updateMeeting", getPath(employee));
	}

	/**
	 * Take a {@link Meeting} and get the URI using Spring Data REST's
	 * {@link EntityLinks}.
	 *
	 * @param meeting
	 */
	private String getPath(Employee employee) {
		return this.entityLinks.linkForSingleResource(employee.getClass(), employee.getId()).toUri().getPath();
	}

}