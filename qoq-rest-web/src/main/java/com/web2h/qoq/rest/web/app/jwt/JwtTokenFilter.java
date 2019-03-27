package com.web2h.qoq.rest.web.app.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.service.authentication.AuthenticationService;
import com.web2h.qoq.rest.messaging.error.ApplicationException;

public class JwtTokenFilter extends OncePerRequestFilter {

	private static final String HEADER_AUTHORIZATION = "Authorization";

	private AuthenticationService authenticationService;

	public JwtTokenFilter(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		String authenticationToken = request.getHeader(HEADER_AUTHORIZATION);

		try {
			User authenticatedUser = authenticationService.authenticateUser(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(getAuthentication(authenticatedUser));
		} catch (ApplicationException ae) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		filterChain.doFilter(request, response);
	}

	public Authentication getAuthentication(User user) {
		AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
		return new UsernamePasswordAuthenticationToken(authenticatedUser, "", authenticatedUser.getAuthorities());
	}
}
