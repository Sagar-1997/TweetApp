package com.tweetapp.TweetApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.tweetapp.TweetApp.dto.PasswordResetRequest;
import com.tweetapp.TweetApp.dto.auth.AuthRequest;
import com.tweetapp.TweetApp.dto.auth.AuthResponse;
import com.tweetapp.TweetApp.mapper.AuthenticationMapper;

@SpringBootTest
public class AuthenticationControllerTest {

	@InjectMocks
	private AuthenticationController authController;

	@Mock
	private AuthenticationMapper authMapper;

	@Test
	public void loginTest() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("david@123");
		authRequest.setPassword("david123");
		AuthResponse authReponse = new AuthResponse();
		authReponse.setToken("token1");
		authReponse.setUsername("david@123");
		Mockito.when(authMapper.login(authRequest)).thenReturn(authReponse);
		ResponseEntity<AuthResponse> actual = authController.login(authRequest);
		assertEquals(authReponse.getUsername(), actual.getBody().getUsername());
	}

	@Test
	public void forgotPassword() {
		String username = "david@123";
		PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
		passwordResetRequest.setConfirmPassword("David@123");
		passwordResetRequest.setPassword("David@123");
		Mockito.when(authMapper.forgotPassword(username, passwordResetRequest)).thenReturn("Updated");
		ResponseEntity<String> actual = authController.forgotPassword(username, passwordResetRequest);
		assertEquals("Updated", actual.getBody());
	}

}
