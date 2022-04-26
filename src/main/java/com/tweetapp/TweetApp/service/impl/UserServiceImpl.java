package com.tweetapp.TweetApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public User getUser(String username) {
		User user = userRepository.findByLoginId(username)
				.orElseThrow(() -> new UserNotFoundException("User with username " + username + " is not found"));
		return user;
	}

}
