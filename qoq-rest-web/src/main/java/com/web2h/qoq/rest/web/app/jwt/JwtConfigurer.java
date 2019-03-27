package com.web2h.qoq.rest.web.app.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.web2h.qoq.rest.core.service.authentication.AuthenticationService;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private AuthenticationService authenticationService;

	public JwtConfigurer(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(authenticationService);
		http.addFilterBefore(customFilter,
				UsernamePasswordAuthenticationFilter.class);
	}
}
