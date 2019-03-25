package com.web2h.qoq.rest.core.service.authentication;

import static com.web2h.qoq.rest.core.error.BadRequestApplicationError.EXPIRED_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.core.error.BadRequestApplicationError.INVALID_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.core.error.NotAuthorizedApplicationError.AUTHENTICATION_CODE_IS_EXPIRED;
import static com.web2h.qoq.rest.core.error.NotAuthorizedApplicationError.DIFFERENT_USER_IN_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.core.error.NotAuthorizedApplicationError.DIFFERENT_USER_LINKED_TO_CODE;
import static com.web2h.qoq.rest.core.error.NotFoundApplicationError.AUTHENTICATION_CODE_NOT_FOUND;
import static com.web2h.qoq.rest.core.error.NotFoundApplicationError.USER_NOT_FOUND_BY_AUTHENTICATION_TOKEN;
import static com.web2h.qoq.rest.core.error.NotFoundApplicationError.USER_NOT_FOUND_BY_EMAIL;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.web2.qoq.rest.model.entity.user.AuthenticationCode;
import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.core.error.ApplicationException;
import com.web2h.qoq.rest.persistence.repository.user.AuthenticationCodeRepository;
import com.web2h.qoq.rest.persistence.repository.user.UserRepository;
import com.web2h.tools.authentication.JwtTools;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceUnitTest {

	private static final String SECURITY_KEY = "security.key";

	private static final String OTHER_SECURITY_KEY = "other.security.key";

	private static final String JWT_ID = "jwt.id";

	private static final String JWT_ISSUER = "jwt.issuer";

	private static final String USER_EMAIL = "user@email.com";

	private static final String OTHER_USER_EMAIL = "other.user@email.com";

	private static final long JWT_LONG_LIFE_TIME = 1000000;

	private static final long JWT_SHORT_LIFE_TIME = 400;

	private static final String INVALID_JWT = "{not.a.jwt}";

	private static final String AUTHENTICATION_CODE = "B52 A58";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@InjectMocks
	private AuthenticationServiceImpl authenticationService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private AuthenticationCodeRepository authenticationCodeRepository;

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(authenticationService, "jwtSecurityKey", SECURITY_KEY);
		ReflectionTestUtils.setField(authenticationService, "jwtIssuer", JWT_ISSUER);
		ReflectionTestUtils.setField(authenticationService, "jwtLifetime", JWT_LONG_LIFE_TIME);
		when(authenticationCodeRepository.findByCode(anyString())).thenReturn(Optional.empty());
	}

	@Test
	public void givenInvalidJwtWhenAuthenticateUserThenApplicationExceptionIsThrown()
			throws InterruptedException, ApplicationException {

		// Given
		String authenticationToken = INVALID_JWT;

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(INVALID_AUTHENTICATION_TOKEN.getLabel());

		// When
		authenticationService.authenticateUser(authenticationToken);
	}

	@Test
	public void givenValidJwtButEncryptedWithDifferentKeyWhenAuthenticateUserThenApplicationExceptionIsThrown()
			throws ApplicationException {

		// Given
		String authenticationToken = givenValidAuthenticationToken(OTHER_SECURITY_KEY, JWT_LONG_LIFE_TIME);

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(INVALID_AUTHENTICATION_TOKEN.getLabel());

		// When
		authenticationService.authenticateUser(authenticationToken);
	}

	@Test
	public void givenExpiredJwtWhenAuthenticateUserThenApplicationExceptionIsThrown()
			throws InterruptedException, ApplicationException {

		// Given
		String authenticationToken = givenValidAuthenticationToken(SECURITY_KEY, JWT_SHORT_LIFE_TIME);
		Thread.sleep(500);

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(EXPIRED_AUTHENTICATION_TOKEN.getLabel());

		// When
		authenticationService.authenticateUser(authenticationToken);
	}

	@Test
	public void givenUnknownTokenWhenAuthenticateUserThenApplicationExceptionIsThrown()
			throws ApplicationException {

		// Given
		String authenticationToken = givenValidAuthenticationToken(SECURITY_KEY, JWT_LONG_LIFE_TIME);
		when(userRepository.findByAuthenticationToken(anyString())).thenReturn(Optional.empty());

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(USER_NOT_FOUND_BY_AUTHENTICATION_TOKEN.getLabel());

		// When
		authenticationService.authenticateUser(authenticationToken);
	}

	@Test
	public void givenValidTokenAndDifferentUserWhenAuthenticateUserThenApplicationExceptionIsThrown()
			throws ApplicationException {

		// Given
		String authenticationToken = givenValidAuthenticationToken(SECURITY_KEY, JWT_LONG_LIFE_TIME);
		User user = givenUser(OTHER_USER_EMAIL);
		when(userRepository.findByAuthenticationToken(anyString())).thenReturn(Optional.of(user));

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(DIFFERENT_USER_IN_AUTHENTICATION_TOKEN.getLabel());

		// When
		authenticationService.authenticateUser(authenticationToken);
	}

	@Test
	public void givenValidTokenWhenAuthenticateUserThenNewTokenIsReturned()
			throws ApplicationException {

		// Given
		String authenticationToken = givenValidAuthenticationToken(SECURITY_KEY, JWT_LONG_LIFE_TIME);
		when(userRepository.findByAuthenticationToken(anyString())).thenReturn(Optional.of(givenUser(USER_EMAIL)));

		// When
		String newAuthenticationToken = authenticationService.authenticateUser(authenticationToken);

		// Then
		assertNotEquals(authenticationToken, newAuthenticationToken);
		verify(userRepository).save(any(User.class));
	}

	@Test
	public void givenUnknownCodeWhenConfirmCodeThenApplicationExceptionIsThrown() throws ApplicationException {

		// Given
		when(authenticationCodeRepository.findByCode(anyString())).thenReturn(Optional.empty());

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(AUTHENTICATION_CODE_NOT_FOUND.getLabel());

		// When
		authenticationService.confirmUser(AUTHENTICATION_CODE, USER_EMAIL);
	}

	@Test
	public void givenUnknownEmailWhenConfirmCodeThenApplicationExceptionIsThrown() throws ApplicationException {

		// Given
		when(authenticationCodeRepository.findByCode(anyString())).thenReturn(Optional.of(givenAuthenticationCode()));
		when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(USER_NOT_FOUND_BY_EMAIL.getLabel());

		// When
		authenticationService.confirmUser(AUTHENTICATION_CODE, USER_EMAIL);
	}

	@Test
	public void givenDifferentUsersWhenConfirmCodeThenApplicationExceptionIsThrown() throws ApplicationException {

		// Given
		when(authenticationCodeRepository.findByCode(anyString())).thenReturn(Optional.of(givenAuthenticationCode()));
		when(userRepository.findByEmail(OTHER_USER_EMAIL)).thenReturn(Optional.of(givenUser(OTHER_USER_EMAIL)));

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(DIFFERENT_USER_LINKED_TO_CODE.getLabel());

		// When
		authenticationService.confirmUser(AUTHENTICATION_CODE, OTHER_USER_EMAIL);
	}

	@Test
	public void givenExpiredCodeWhenConfirmCodeThenApplicationExceptionIsThrown() throws ApplicationException {

		// Given
		AuthenticationCode authenticationCode = givenAuthenticationCode();
		authenticationCode.setExpiration(new Date(System.currentTimeMillis() - 100));

		when(authenticationCodeRepository.findByCode(anyString())).thenReturn(Optional.of(authenticationCode));
		when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(givenUser(USER_EMAIL)));

		thrown.expect(ApplicationException.class);
		thrown.expectMessage(AUTHENTICATION_CODE_IS_EXPIRED.getLabel());

		// When
		authenticationService.confirmUser(AUTHENTICATION_CODE, USER_EMAIL);
	}

	@Test
	public void givenValidCodeWhenConfirmCodeThenSucceed() throws ApplicationException {

		// Given
		AuthenticationCode authenticationCode = givenAuthenticationCode();
		authenticationCode.setExpiration(new Date(System.currentTimeMillis() + 1000));

		when(authenticationCodeRepository.findByCode(anyString())).thenReturn(Optional.of(authenticationCode));
		when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(givenUser(USER_EMAIL)));

		// When
		String authenticationToken = authenticationService.confirmUser(AUTHENTICATION_CODE, USER_EMAIL);

		// Then
		assertNotNull(authenticationToken);
		verify(authenticationCodeRepository).delete(any(AuthenticationCode.class));
		verify(userRepository).save(any(User.class));
	}

	@Test
	public void givenEmailOfAnExisitingUserWhenCreateNewAuthenticationCodeThenSaveUserIsNeverCalled() throws ApplicationException {

		// Given
		User user = givenUser(USER_EMAIL);
		when(userRepository.findByEmail(eq(user.getEmail()))).thenReturn(Optional.of(user));

		// When
		authenticationService.requestLoginCode(user.getEmail());

		// Then
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	public void givenEmailOfAnUnexisitingUserWhenCreateNewAuthenticationCodeThenSaveUserIsCalled() throws ApplicationException {

		// Given
		when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

		// When
		authenticationService.requestLoginCode(USER_EMAIL);

		// Then
		verify(userRepository).save(any(User.class));
	}

	@Test
	public void givenEmailWhenCreateNewAuthenticationCodeThenCodeIsCreated() throws ApplicationException {

		// Given
		User user = givenUser(USER_EMAIL);
		when(userRepository.findByEmail(eq(user.getEmail()))).thenReturn(Optional.of(user));

		// When
		authenticationService.requestLoginCode(user.getEmail());

		// Then
		verify(authenticationCodeRepository).findByCode(anyString());
		verify(authenticationCodeRepository).save(any(AuthenticationCode.class));
	}

	private String givenValidAuthenticationToken(String securityKey, long lifetime) {
		return JwtTools.createJwt(securityKey, JWT_ID, JWT_ISSUER, USER_EMAIL, lifetime);
	}

	private User givenUser(String email) {
		User user = new User(email);

		return user;
	}

	private AuthenticationCode givenAuthenticationCode() {
		AuthenticationCode authenticationCode = new AuthenticationCode();
		authenticationCode.setCode(AUTHENTICATION_CODE);
		authenticationCode.setUser(givenUser(USER_EMAIL));

		return authenticationCode;
	}
}
