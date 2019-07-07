package com.natandanielapps.consensysbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ConsensysBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsensysBookingApplication.class, args);
	}
}
