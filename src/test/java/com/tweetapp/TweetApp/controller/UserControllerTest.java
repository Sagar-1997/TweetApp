package com.tweetapp.TweetApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.tweetapp.TweetApp.dto.UserResponse;
import com.tweetapp.TweetApp.mapper.UserMapper;

@SpringBootTest
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserMapper userMapper;

	private UserResponse userReponse;

	@BeforeEach
	public void setUp() {
		userReponse = new UserResponse();
		userReponse.setContactNumber("8978526345");
		userReponse.setEmail("a@gail.com");
		userReponse.setFirstName("Q");
		userReponse.setLastName("S");
		userReponse.setLoginId("a@123");
	}

	@Test
	public void getAllUsersTest() {
		List<UserResponse> userList = new ArrayList<UserResponse>();
		userList.add(userReponse);
		Mockito.when(userMapper.getAllUsers()).thenReturn(userList);
		ResponseEntity<List<UserResponse>> actual = userController.getAllUsers();
		assertEquals(userList, actual.getBody());
	}

	@Test
	public void getUserTest() {
		List<UserResponse> userList = new ArrayList<UserResponse>();
		userList.add(userReponse);
		Mockito.when(userMapper.getUsers("a@123")).thenReturn(userList);
		ResponseEntity<List<UserResponse>> actual = userController.getUser("a@123");
		assertEquals(userList, actual.getBody());
	}

}
