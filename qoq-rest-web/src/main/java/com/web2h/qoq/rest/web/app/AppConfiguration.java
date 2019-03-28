package com.web2h.qoq.rest.web.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

	@Bean(name = "jsonMapper")
	public ObjectMapper getJsonMapper() {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.setSerializationInclusion(Include.NON_NULL);
		return jsonMapper;
	}
}
