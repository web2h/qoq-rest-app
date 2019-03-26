package com.web2h.qoq.rest.web.app;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.web2h.qoq.rest.persistence.repository")
@EntityScan("com.web2.qoq.rest.model.entity")
@ComponentScan("com.web2h.qoq.rest")
public class Application {

	public static void main(String[] args) {
		run(Application.class, args);
	}
}
