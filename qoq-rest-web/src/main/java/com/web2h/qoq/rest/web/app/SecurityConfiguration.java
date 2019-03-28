package com.web2h.qoq.rest.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.web2h.qoq.rest.core.service.authentication.AuthenticationService;
import com.web2h.qoq.rest.web.app.jwt.JwtTokenFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/**").permitAll()
				.anyRequest().authenticated()
				// .antMatchers(HttpMethod.GET, "/vehicles/**").permitAll()
				// .antMatchers(HttpMethod.DELETE, "/vehicles/**").hasRole("ADMIN")
				// .antMatchers(HttpMethod.GET, "/v1/vehicles/**").permitAll()
				.and()
				.addFilterBefore(getJwtTokenFilter(), BasicAuthenticationFilter.class);
	}

	// @Bean
	public JwtTokenFilter getJwtTokenFilter() {
		return new JwtTokenFilter(authenticationService);
	}

	// @Bean
	public FilterRegistrationBean<JwtTokenFilter> registration(JwtTokenFilter filter) {
		FilterRegistrationBean<JwtTokenFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled(false);
		return registration;
	}
}
