package com.tweetapp.TweetApp.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	User user;

	@BeforeEach
	public void setUp() {
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
	public void getAllUsersTest() {
		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		Mockito.when(userRepository.findAll()).thenReturn(userlist);
		List<User> actual = userService.getAllUsers();
		assertEquals(userlist, actual);
	}

	@Test
	public void getUsersTest() {
		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		Mockito.when(userRepository.findAllByLoginIdContaining("a@123")).thenReturn(userlist);
		List<User> actual = userService.getUsers("a@123");
		assertEquals(userlist, actual);
	}

	@Test
	public void getAllUsersIsEmptyTest() {
		List<User> userlist = new ArrayList<User>();
		Mockito.when(userRepository.findAllByLoginIdContaining("a@123")).thenReturn(userlist);
		UserNotFoundException actual = assertThrows(UserNotFoundException.class, () -> userService.getUsers("a@123"));
		assertEquals("No matching user is found", actual.getUserNotFound());
	}
}
