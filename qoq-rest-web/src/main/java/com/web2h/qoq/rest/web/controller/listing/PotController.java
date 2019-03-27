package com.web2h.qoq.rest.web.controller.listing;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.qoq.rest.messaging.listing.pot.creation.PotCreationRequest;

@RestController
@RequestMapping("/pot")
public class PotController {

	private Logger logger = LoggerFactory.getLogger(PotController.class);

	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody @Valid PotCreationRequest potCreationRequest, BindingResult result) {

		logger.info("Pot creation");

		if (result.hasErrors()) {
			logger.warn("Invalid data");
		}
		return null;
	}
}
