package com.tweetapp.TweetApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		log.info("Inside getAllUsers method in UserServiceImpl class");
		List<User> users = userRepository.findAll();
		log.info("All Users => {}", users);
		return users;
	}

	@Override
	public List<User> getUsers(String username) {
		log.info("Inside getUser method in UserServiceImpl class");
		List<User> users = userRepository.findAllByLoginIdContaining(username);
		if (users.isEmpty() || users == null) {
			log.info("Users with "+username+" is not found");
			throw new UserNotFoundException("No matching user is found");
		}
		log.info("All users by username => {}", users);
		return users;
	}

}
