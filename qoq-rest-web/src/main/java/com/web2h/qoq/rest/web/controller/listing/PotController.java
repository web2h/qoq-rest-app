package com.web2h.qoq.rest.web.controller.listing;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.messaging.QoqResponse;
import com.web2h.qoq.rest.messaging.listing.pot.PotCreationMessageAdapter;
import com.web2h.qoq.rest.messaging.listing.pot.PotCreationRequest;
import com.web2h.qoq.rest.messaging.listing.pot.PotCreationResponse;
import com.web2h.qoq.rest.service.error.ApplicationException;

@RestController
@RequestMapping("/pot")
public class PotController {

	@Autowired
	private PotCreationMessageAdapter potCreationMessageAdapter;

	private Logger logger = LoggerFactory.getLogger(PotController.class);

	@PostMapping("/create")
	public ResponseEntity<QoqResponse> create(@RequestBody @Valid PotCreationRequest request, BindingResult result) {

		logger.info("Pot creation");

		if (result.hasErrors()) {
			logger.warn("Invalid data");
		}

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PotCreationResponse response;
			potCreationMessageAdapter.callService(request, user);
			return new ResponseEntity<QoqResponse>(response, HttpStatus.OK);
		} catch (ApplicationException ae) {
			logger.error("Error while getting login code - {}",
					ae.getApplicationError().getLabel());
			return buildErrorResponse(ae.getApplicationError());
		}

	}
}
