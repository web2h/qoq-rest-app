package com.web2h.qoq.rest.web.controller.listing;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.qoq.rest.messaging.listing.pot.creation.PotCreationRequest;

@RestController
@RequestMapping("/pot")
public class PotController {

	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody PotCreationRequest potCreationRequest) {
		return null;
	}
}
