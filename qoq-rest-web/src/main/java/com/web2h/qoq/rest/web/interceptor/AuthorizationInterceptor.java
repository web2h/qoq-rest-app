package com.web2h.qoq.rest.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.service.authentication.AuthenticationService;
import com.web2h.qoq.rest.messaging.QoqResponse;
import com.web2h.qoq.rest.service.error.ApplicationException;
import com.web2h.qoq.rest.web.app.jwt.AuthenticatedUser;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	private static final String HEADER_AUTHORIZATION = "Authorization";

	private static final String CONTENT_TYPE_JSON = "application/json";

	private static final String CHARACTER_ENCODING_UTF8 = "UTF-8";

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private ApplicationContext context;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		String authenticationToken = request.getHeader(HEADER_AUTHORIZATION);

		try {
			securityContext.setAuthentication(null);
			User user = authenticationService.authenticateUser(authenticationToken);
			securityContext.setAuthentication(getAuthentication(user));
		} catch (ApplicationException ae) {
			try {
				// TODO Log exception
				QoqResponse responseBody = new QoqResponse();
				responseBody.addError(ae.getApplicationError());

				ObjectMapper mapper = getJsonMapper();
				String json = mapper.writeValueAsString(responseBody);
				PrintWriter out = response.getWriter();
				response.setContentType(CONTENT_TYPE_JSON);
				response.setCharacterEncoding(CHARACTER_ENCODING_UTF8);
				out.print(json);
				out.flush();

				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			} catch (IOException ioe) {
				// TODO Log error
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
			return false;
		}
		return true;
	}

	public Authentication getAuthentication(User user) {
		AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
		return new UsernamePasswordAuthenticationToken(authenticatedUser, "", authenticatedUser.getAuthorities());
	}

	private ObjectMapper getJsonMapper() {
		return (ObjectMapper) context.getBean("jsonMapper");
	}
}
