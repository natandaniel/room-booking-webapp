package com.natandanielapps.consensysbooking.services.infrastructure;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class MeetingRepoRestClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RestTemplateFactory restTemplateFactory;

	private String meetingRepoUrl = "http://localhost:8080/api/meetings/";

	public ResponseEntity<Meeting> getMeeting(String meetingId) throws RestClientException {
		
		log.info("getting meeting " + meetingId);

		RestTemplate restTemplate = restTemplateFactory.getObject();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));

		ResponseEntity<Meeting> meetingEntity = restTemplate.getForEntity(meetingRepoUrl + meetingId, Meeting.class);

		log.info("meeting fetched");
		log.info("meeting : " + meetingEntity.getBody().toString());

		return meetingEntity;

	}

	public ResponseEntity<Meeting> updateMeeting(String meetingId, Meeting meetingToUpdate) throws RestClientException {
		
		log.info("updating meeting " + meetingId);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<Meeting> meetingHttpEntity = new HttpEntity<>(meetingToUpdate, httpHeaders);

		RestTemplate restTemplate = restTemplateFactory.getObject();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));

		ResponseEntity<Meeting> meetingEntity = restTemplate.exchange(meetingRepoUrl + meetingId, HttpMethod.PUT,
				meetingHttpEntity, Meeting.class);
		
		log.info("meeting updated");
		log.info("meeting : " + meetingEntity.getBody().toString());

		return meetingEntity;
	}

}
