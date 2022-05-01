package com.tweetapp.TweetApp.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.UserResponse;
import com.tweetapp.TweetApp.service.UserService;

@SpringBootTest
public class UserMapperTest {

	@Mock
	private UserService userService;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private UserMapper userMapper;
	
	User user ;
	UserResponse response;
	
	@BeforeEach
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setConfirmPassword("123");
		user.setContactNumber("9083647826");
		user.setEmail("a@gmail.com");
		user.setFirstName("first");
		user.setLastName("last");
		user.setLoginId("a@123");
		user.setPassword("123");
	    response = new UserResponse();
		response.setContactNumber("9083647826");
		response.setEmail("a@gmail.com");
		response.setFirstName("first");
		response.setLastName("last");
		response.setLoginId("a@123");
	}
	
	@Test
	public void getAllUsersTest()
	{
		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		List<UserResponse> responseList = new ArrayList<UserResponse>();
		responseList.add(response);
		Mockito.when(userService.getAllUsers()).thenReturn(userlist);
		Mockito.when(modelMapper.map(user,UserResponse.class)).thenReturn(response);
		List<UserResponse> actual = userMapper.getAllUsers();
		assertEquals(responseList,actual);
	}
	@Test
	public void getUsersTest()
	{
		String username ="a@123";
		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		List<UserResponse> responseList = new ArrayList<UserResponse>();
		responseList.add(response);
		Mockito.when(userService.getUsers(username)).thenReturn(userlist);
		Mockito.when(modelMapper.map(user,UserResponse.class)).thenReturn(response);
		List<UserResponse> actual = userMapper.getUsers(username);
		assertEquals(responseList,actual);
	}
}
