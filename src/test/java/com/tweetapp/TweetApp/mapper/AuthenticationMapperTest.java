package com.tweetapp.TweetApp.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.PasswordResetRequest;
import com.tweetapp.TweetApp.dto.RegistrationRequest;
import com.tweetapp.TweetApp.dto.auth.AuthRequest;
import com.tweetapp.TweetApp.dto.auth.AuthResponse;
import com.tweetapp.TweetApp.service.impl.AuthenticationServiceImpl;

@SpringBootTest
public class AuthenticationMapperTest {

	@InjectMocks
	private AuthenticationMapper authMapper;

	@Mock
	private AuthenticationServiceImpl authService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	BindingResult bindResult;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void registerUserTest() {
		RegistrationRequest request = new RegistrationRequest();
		request.setConfirmPassword("123");
		request.setContactNumber("9876543210");
		request.setEmail("a@gmail.com");
		request.setFirstName("A");
		request.setLastName("A");
		request.setLoginId("A@123");
		request.setPassword("123");
		User userEntity = new User();
		userEntity.setConfirmPassword("123");
		userEntity.setContactNumber("9876543210");
		userEntity.setEmail("a@gmail.com");
		userEntity.setFirstName("A");
		userEntity.setLastName("A");
		userEntity.setLoginId("A@123");
		userEntity.setPassword("123");
		Mockito.when(modelMapper.map(request, User.class)).thenReturn(userEntity);
		Mockito.when(authService.registerUser(userEntity)).thenReturn("register");
		String registerUser = authMapper.registerUser(request, bindResult);
		assertEquals("register", registerUser);
	}

	@Test
	public void loginTest() {
		AuthRequest request = new AuthRequest();
		request.setPassword("123");
		request.setUsername("a@123");
		AuthResponse response = new AuthResponse();
		response.setExpirationTime(new Date());
		response.setToken("token 1");
		response.setUsername("a@123");
		Mockito.when(authService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		AuthResponse actual = authMapper.login(request);
		assertEquals(response, actual);
	}

	@Test
	public void forgotPasswordTest() {
		String username = "a@123";
		PasswordResetRequest request = new PasswordResetRequest();
		request.setConfirmPassword("123");
		request.setPassword("123");
		Mockito.when(authService.forgotPassword(username, request.getPassword(), request.getConfirmPassword()))
				.thenReturn("password change");
		String actual = authMapper.forgotPassword(username, request);
		assertEquals("password change", actual);
	}
}
