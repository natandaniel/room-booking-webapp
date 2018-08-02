package com.natandanielapps.consensysbooking.services.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "meetings")
@EntityListeners(AuditingEntityListener.class)
public class Meeting implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingStartTime;

	private boolean isMeetingBookable;

	private boolean isMeetingBooked;
	
	private String currentUsername;

	@ManyToOne
	@JoinColumn(name = "room_id")
	@JsonIgnore
	private Room room;

	@OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Booking> bookings;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonIgnore
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@JsonIgnore
	private Date updatedAt;

	public Meeting() {

	}

	public Meeting(@NotBlank Date meetingStartTime, @NotBlank boolean isMeetingBookable,
			@NotBlank boolean isMeetingBooked, @NotBlank Room room) {
		this.meetingStartTime = meetingStartTime;
		this.isMeetingBookable = isMeetingBookable;
		this.isMeetingBooked = isMeetingBooked;
		this.room = room;
	}
}
