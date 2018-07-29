package com.natandanielapps.consensysbooking.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.natandanielapps.consensysbooking.model.Meeting;

@Component
@RepositoryEventHandler(Meeting.class)
public class EventHandler {

	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;

	@Autowired
	public EventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	@HandleAfterSave
	public void updateMeeting(Meeting meeting) {
		System.out.println("Hello");
		this.websocket.convertAndSend(WebSocketConfiguration.MESSAGE_PREFIX + "/updateMeeting", getPath(meeting));
	}

	/**
	 * Take a {@link Meeting} and get the URI using Spring Data REST's
	 * {@link EntityLinks}.
	 *
	 * @param meeting
	 */
	private String getPath(Meeting meeting) {
		return this.entityLinks.linkForSingleResource(meeting.getClass(), meeting.getId()).toUri().getPath();
	}

}