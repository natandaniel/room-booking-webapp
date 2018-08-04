package com.natandanielapps.consensysbooking.services.infrastructure;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.natandanielapps.consensysbooking.services.entities.Meeting;
import com.natandanielapps.consensysbooking.services.infrastructure.tools.RestTemplateFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * REST client to update instances of {@link Meeting} in database.
 *
 */
@Service
@Slf4j
public class MeetingRepoRestClient {

	@Autowired
	RestTemplateFactory restTemplateFactory;

	private String meetingRepoUrl = "http://localhost:8080/api/meetings/";

	/**
	 * Updates a {@link Meeting}
	 * @param meetingId a {@link Meeting} identifier
	 * @param meetingToUpdate instance of {@link Meeting} to update
	 * @throws RestClientException
	 */
	public void updateMeeting(String meetingId, Meeting meetingToUpdate) throws RestClientException {

		log.info("updating meeting " + meetingId);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.add("Content-Type", "application/json");

		HttpEntity<Meeting> meetingHttpEntity = new HttpEntity<>(meetingToUpdate, httpHeaders);

		RestTemplate restTemplate = restTemplateFactory.getObject();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));

		ResponseEntity<Meeting> meetingEntity = restTemplate.exchange(meetingRepoUrl + meetingId, HttpMethod.PUT,
				meetingHttpEntity, Meeting.class);

		log.info("meeting updated");
		log.info("meeting : " + meetingEntity.getBody().toString());
	}
}
