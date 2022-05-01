package com.tweetapp.TweetApp.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("inside loadUserByUsername method of MyUserDetailsService");
		User user = userRepository.findByLoginId(username)
				.orElseThrow(() -> new UserNotFoundException("User with " + username + " is not found"));
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
	}

}
