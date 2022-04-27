package com.tweetapp.TweetApp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetApp.dto.user.UserResponse;
import com.tweetapp.TweetApp.mapper.UserMapper;

@RestController
@RequestMapping("/api/v1.0/tweets/")
public class UserController {
	
	private UserMapper userMapper;
	public UserController(UserMapper mapper) {
		this.userMapper=mapper;
	}
	
	@GetMapping("/users/all")
	public ResponseEntity<List<UserResponse>> getAllUsers()
	{
		return ResponseEntity.ok().body(userMapper.getAllUsers());
	}
	
	@GetMapping("/user/search/{username}")
	public ResponseEntity<List<UserResponse>> getUser(@PathVariable("username") String username) {
		return ResponseEntity.ok().body(userMapper.getUser(username));
	}
	
}
