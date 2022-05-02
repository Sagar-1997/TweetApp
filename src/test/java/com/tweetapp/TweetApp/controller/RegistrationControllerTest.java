package com.tweetapp.TweetApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.tweetapp.TweetApp.dto.RegistrationRequest;
import com.tweetapp.TweetApp.mapper.AuthenticationMapper;

@SpringBootTest
public class RegistrationControllerTest {

	@InjectMocks
	private RegistrationController registrationController;

	@Mock
	private AuthenticationMapper authMapper;
	@Mock
	private BindingResult bindResult;

	@Test
	public void registration() {
		RegistrationRequest request = new RegistrationRequest();
		request.setConfirmPassword("123");
		request.setContactNumber("9876543210");
		request.setEmail("a@gmail.com");
		request.setFirstName("A");
		request.setLastName("A");
		request.setLoginId("A@123");
		request.setPassword("123");
		Mockito.when(authMapper.registerUser(request, bindResult)).thenReturn("User Added");
		ResponseEntity<String> actual = registrationController.registration(request, bindResult);
		assertEquals("User Added", actual.getBody());
	}
}
