package com.tweetapp.TweetApp.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.auth.AuthResponse;
import com.tweetapp.TweetApp.exception.EmailException;
import com.tweetapp.TweetApp.exception.PasswordException;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.exception.UsernameException;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.security.JWTProvider;
import com.tweetapp.TweetApp.service.impl.AuthenticationServiceImpl;

@SpringBootTest
public class AuthenticationServiceTest {

	@Mock
	private JWTProvider jwtProvider;

	@Mock
	private AuthenticationManager authManager;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthenticationServiceImpl authService;

	@Mock
	private Authentication auth;

	User user;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setConfirmPassword("123");
		user.setContactNumber("9849364738");
		user.setEmail("a11@gmail.com");
		user.setFirstName("first");
		user.setLastName("last");
		user.setLoginId("a@123");
		user.setPassword("123");
	}

	@Test
	@DirtiesContext
	public void registerUserTest() {
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(user)).thenReturn(user);
		String actual = authService.registerUser(user);
		assertEquals("User is successfully registered", actual);
	}

	@Test
	public void registerUserPasswordNotEqualTest() {
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.empty());
		user.setConfirmPassword("12345");
		PasswordException actual = assertThrows(PasswordException.class, () -> authService.registerUser(user));
		assertEquals("Password do not match", actual.getPasswordError());
	}

	@Test
	public void registerUserUsernameIsPresentTest() {
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(user));
		UsernameException actual = assertThrows(UsernameException.class, () -> authService.registerUser(user));
		assertEquals("Username already present", actual.getUsernameError());
	}

	@Test
	public void registerUserEmailIsPresentTest() {
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.empty());
		EmailException actual = assertThrows(EmailException.class, () -> authService.registerUser(user));
		assertEquals("Email already present", actual.getEmailError());
	}

	@Test
	public void loginTest() {
		AuthResponse response = new AuthResponse();
		response.setExpirationTime(new Date());
		response.setUsername("a@123");
		response.setToken("token1");
		Mockito.when(authManager
				.authenticate(new UsernamePasswordAuthenticationToken(Mockito.anyObject(), user.getPassword())))
				.thenReturn(auth);
		Object principal = auth.getPrincipal();
		Mockito.when(auth.getPrincipal()).thenReturn(principal);
		UserDetails user1 = new org.springframework.security.core.userdetails.User(user.getLoginId(),
				user.getPassword(), new ArrayList<>());
		Mockito.when(jwtProvider.generateToken(user1)).thenReturn("token1");
		Mockito.when(jwtProvider.extractExpiration(Mockito.anyString())).thenReturn(new Date());
		AuthResponse actual = authService.login(user.getLoginId(), user.getPassword());
		assertEquals("a@123", actual.getUsername());
	}

	@Test
	@DirtiesContext
	public void forgotPasswordTest() {
		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(user)).thenReturn(user);
		String actual = authService.forgotPassword(user.getLoginId(), user.getPassword(), user.getConfirmPassword());
		assertEquals("Password for a@123 is changed !!", actual);
	}

	@Test
	public void forgotPasswordNotEqualTest() {
		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(user));
		PasswordException actual = assertThrows(PasswordException.class,
				() -> authService.forgotPassword(user.getLoginId(), user.getPassword(), "12345"));
		assertEquals("Password do not match", actual.getPasswordError());
	}

	@Test
	public void forgotPasswordUserNotFoundTest() {
		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.empty());
		UserNotFoundException actual = assertThrows(UserNotFoundException.class,
				() -> authService.forgotPassword(user.getLoginId(), user.getPassword(), user.getConfirmPassword()));
		assertEquals("User with a@123 is not found", actual.getUserNotFound());
	}
}
