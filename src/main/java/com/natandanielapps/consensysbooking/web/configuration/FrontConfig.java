package com.natandanielapps.consensysbooking.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class FrontConfig {
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
}

