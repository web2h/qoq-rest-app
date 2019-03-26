package com.web2h.qoq.rest.core.service.authentication;

import static com.web2h.qoq.rest.messaging.error.NotAuthorizedApplicationError.AUTHENTICATION_CODE_IS_EXPIRED;
import static com.web2h.qoq.rest.messaging.error.NotAuthorizedApplicationError.DIFFERENT_USER_IN_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.messaging.error.NotAuthorizedApplicationError.DIFFERENT_USER_LINKED_TO_CODE;
import static com.web2h.qoq.rest.messaging.error.NotAuthorizedApplicationError.EXPIRED_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.messaging.error.NotAuthorizedApplicationError.INVALID_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.messaging.error.NotAuthorizedApplicationError.MISSING_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.messaging.error.NotAuthorizedApplicationError.USER_NOT_FOUND_BY_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.messaging.error.NotFoundApplicationError.AUTHENTICATION_CODE_NOT_FOUND;
import static com.web2h.qoq.rest.messaging.error.NotFoundApplicationError.USER_NOT_FOUND_BY_EMAIL;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.web2.qoq.rest.model.entity.user.AuthenticationCode;
import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.messaging.error.ApplicationException;
import com.web2h.qoq.rest.persistence.repository.user.AuthenticationCodeRepository;
import com.web2h.qoq.rest.persistence.repository.user.UserRepository;
import com.web2h.tools.authentication.CodeTools;
import com.web2h.tools.authentication.JwtTools;
import com.web2h.tools.date.DateTools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationCodeRepository authenticationCodeRepository;

	@Value("${authentication.jwt.security.key}")
	private String jwtSecurityKey;

	@Value("${authentication.jwt.issuer}")
	private String jwtIssuer;

	@Value("${authentication.jwt.lifetime}")
	private long jwtLifetime;

	@Value("${authentication.code.lifetime}")
	private long codeLifetime;

	@Override
	public String authenticateUser(String authenticationToken) throws ApplicationException {

		if (authenticationToken == null) {
			throw new ApplicationException(MISSING_AUTHENTICATION_TOKEN);
		}

		String userEmailInToken = null;
		try {
			Claims claims = JwtTools.decodeJwt(jwtSecurityKey, authenticationToken);
			userEmailInToken = claims.getSubject();
		} catch (ExpiredJwtException mje) {
			throw new ApplicationException(EXPIRED_AUTHENTICATION_TOKEN);
		} catch (MalformedJwtException | SignatureException e) {
			throw new ApplicationException(INVALID_AUTHENTICATION_TOKEN);
		}

		User user = userRepository.findByAuthenticationToken(authenticationToken)
				.orElseThrow(() -> new ApplicationException(USER_NOT_FOUND_BY_AUTHENTICATION_TOKEN));

		if (!user.getEmail().equals(userEmailInToken)) {
			throw new ApplicationException(DIFFERENT_USER_IN_AUTHENTICATION_TOKEN);
		}

		// Building a new token with extended lifetime
		return createAuthenticationToken(user);
	}

	@Override
	public String confirmUser(String code, String email) throws ApplicationException {
		AuthenticationCode authenticationCode = authenticationCodeRepository.findByCode(code)
				.orElseThrow(() -> new ApplicationException(AUTHENTICATION_CODE_NOT_FOUND));

		User user = userRepository.findByEmail(email).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL));

		if (!user.equals(authenticationCode.getUser())) {
			throw new ApplicationException(DIFFERENT_USER_LINKED_TO_CODE);
		}

		if (DateTools.isBefore(authenticationCode.getExpiration(), new Date())) {
			throw new ApplicationException(AUTHENTICATION_CODE_IS_EXPIRED);
		}

		authenticationCodeRepository.delete(authenticationCode);

		return createAuthenticationToken(user);
	}

	@Override
	public void requestLoginCode(String email) throws ApplicationException {

		User user = userRepository.findByEmail(email).orElseGet(() -> createNewUser(email));

		/* /AuthenticationCode authenticationCode = */createNewAuthenticationCode(user);

		// TODO Send authentication code to user's email address
	}

	private User createNewUser(String email) {
		User user = new User(email);
		return userRepository.save(user);
	}

	private String createAuthenticationToken(User user) {
		String authenticationToken = JwtTools.createJwt(jwtSecurityKey, String.valueOf(user.getId()), jwtIssuer,
				user.getEmail(), jwtLifetime);

		user.setAuthenticationToken(authenticationToken);
		userRepository.save(user);

		return authenticationToken;
	}

	private AuthenticationCode createNewAuthenticationCode(User user) {
		AuthenticationCode authenticationCode = new AuthenticationCode();
		authenticationCode.setUser(user);
		authenticationCode.setExpiration(new Date(System.currentTimeMillis() + codeLifetime));
		do {
			String code = CodeTools.buildAuthenticationCode();
			if (!authenticationCodeRepository.findByCode(code).isPresent()) {
				authenticationCode.setCode(code);
			}
		} while (authenticationCode.getCode() == null);
		return authenticationCodeRepository.save(authenticationCode);
	}
}
