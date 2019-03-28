package com.web2h.qoq.rest.web.controller.auth;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2h.qoq.rest.messaging.QoqResponse;
import com.web2h.qoq.rest.messaging.authentication.AuthenticationCodeConfirmationMessageAdapter;
import com.web2h.qoq.rest.messaging.authentication.AuthenticationCodeConfirmationRequest;
import com.web2h.qoq.rest.messaging.authentication.AuthenticationCodeConfirmationResponse;
import com.web2h.qoq.rest.messaging.authentication.AuthenticationCodeMessageAdapter;
import com.web2h.qoq.rest.messaging.authentication.AuthenticationCodeRequest;
import com.web2h.qoq.rest.messaging.authentication.AuthenticationCodeResponse;
import com.web2h.qoq.rest.service.error.ApplicationError;
import com.web2h.qoq.rest.service.error.ApplicationException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationCodeMessageAdapter authenticationCodeMessageAdapter;

	@Autowired
	private AuthenticationCodeConfirmationMessageAdapter authenticationCodeConfirmationMessageAdapter;

	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@PostMapping("/request")
	public ResponseEntity<QoqResponse> requestCode(@RequestBody @Valid AuthenticationCodeRequest request,
			BindingResult result) {

		logger.info(request.toString());

		if (result.hasErrors()) {
			logger.warn("Invalid data");
			return buildValidationErrorsResponse(result.getAllErrors());
		}

		try {
			AuthenticationCodeResponse response = authenticationCodeMessageAdapter.callService(request);
			return new ResponseEntity<QoqResponse>(response, HttpStatus.OK);
		} catch (ApplicationException ae) {
			logger.error("Error while getting login code - {}",
					ae.getApplicationError().getLabel());
			return buildErrorResponse(ae.getApplicationError());
		}
	}

	@PostMapping("/confirm")
	public ResponseEntity<QoqResponse> confirmCode(
			@RequestBody @Valid AuthenticationCodeConfirmationRequest request, BindingResult result) {

		logger.info(request.toString());

		if (result.hasErrors()) {
			logger.warn("Invalid data");
			return buildValidationErrorsResponse(result.getAllErrors());
		}

		try {
			AuthenticationCodeConfirmationResponse response = authenticationCodeConfirmationMessageAdapter
					.callService(request);
			return new ResponseEntity<QoqResponse>(response, HttpStatus.OK);
		} catch (ApplicationException ae) {
			logger.error("Error while getting login code - {}",
					ae.getApplicationError().getLabel());
			return buildErrorResponse(ae.getApplicationError());
		}
	}

	private ResponseEntity<QoqResponse> buildErrorResponse(ApplicationError applicationError) {
		QoqResponse response = new QoqResponse();
		response.addError(applicationError);
		return new ResponseEntity<QoqResponse>(response, applicationError.getHttpStatus());
	}

	private ResponseEntity<QoqResponse> buildValidationErrorsResponse(List<ObjectError> errors) {
		QoqResponse response = new QoqResponse();
		for (ObjectError error : errors) {
			response.addValidationError((FieldError) error);
		}
		return new ResponseEntity<QoqResponse>(response, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<QoqResponse> buildOkResponse() {
		return new ResponseEntity<QoqResponse>(new QoqResponse(), HttpStatus.OK);
	}
}
