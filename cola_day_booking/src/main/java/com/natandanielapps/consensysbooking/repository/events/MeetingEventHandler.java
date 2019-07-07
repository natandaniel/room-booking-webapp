package com.natandanielapps.consensysbooking.repository.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.services.entities.Employee;
import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.web.websockets.WebSocketConfiguration;

/**
 * Meeting repository event handler.
 * 	
 *
 */
@Component
@RepositoryEventHandler(Meeting.class)
public class MeetingEventHandler {

	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;

	@Autowired
	EmployeeRepository meetings;

	@Autowired
	public MeetingEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	/**
	 * Sends message to all clients that have subscribed to the /updateMeeting topic
	 * when a {@link Meeting} entity is updated.
	 * 
	 * @param meeting
	 */
	@HandleAfterSave
	public void updateMeeting(Meeting meeting) {

		this.websocket.convertAndSend(WebSocketConfiguration.MESSAGE_PREFIX + "/updateMeeting", getPath(meeting));
	}

	/**
	 * Take a {@link Employee} and get the URI using Spring Data REST's
	 * {@link EntityLinks}.
	 *
	 * @param meeting
	 */
	private String getPath(Meeting meeting) {
		return this.entityLinks.linkForSingleResource(meeting.getClass(), meeting.getId()).toUri().getPath();
	}

}