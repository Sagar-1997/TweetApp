package com.tweetapp.TweetApp.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.repository.UserRepository;

@SpringBootTest
public class MyUserDetailsServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private MyUserDetailsService userService;

	@Test
	public void loadUserByUsernameTest() {
		String username = "a@123";
		UserDetails user = new org.springframework.security.core.userdetails.User("a@123", "123", new ArrayList<>());
		User user1 = new User();
		user1.setConfirmPassword("123");
		user1.setContactNumber("9834925367");
		user1.setEmail("a@gmail.com");
		user1.setFirstName("first");
		user1.setLastName("last");
		user1.setLoginId("a@123");
		user1.setPassword("123");
		Mockito.when(userRepository.findByLoginId(username)).thenReturn(java.util.Optional.of(user1));
		UserDetails actual = userService.loadUserByUsername(username);
		assertEquals(user.getUsername(), actual.getUsername());
	}

	@Test
	public void loadUserByUsernameUserNotFoundTest() {
		String username = "a@123";
		Mockito.when(userRepository.findByLoginId(username)).thenReturn(java.util.Optional.empty());
		UserNotFoundException actual = assertThrows(UserNotFoundException.class,
				() -> userService.loadUserByUsername(username));
		assertEquals("User with a@123 is not found", actual.getUserNotFound());
	}
}
