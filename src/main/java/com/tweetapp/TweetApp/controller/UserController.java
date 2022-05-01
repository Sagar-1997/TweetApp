package com.tweetapp.TweetApp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetApp.dto.UserResponse;
import com.tweetapp.TweetApp.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/tweets")
public class UserController {

	private UserMapper userMapper;

	public UserController(UserMapper mapper) {
		this.userMapper = mapper;
	}

	/* Get all users */
	@GetMapping("/users/all")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		log.info("inside getAllUsers method of UserController");
		return ResponseEntity.ok().body(userMapper.getAllUsers());
	}

	/* get user by username */
	@GetMapping("/user/search/{username}")
	public ResponseEntity<List<UserResponse>> getUser(@PathVariable("username") String username) {
		log.info("inside getUser method of UserController");
		return ResponseEntity.ok().body(userMapper.getUsers(username));
	}

}
