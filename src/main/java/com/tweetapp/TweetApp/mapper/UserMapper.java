package com.tweetapp.TweetApp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.UserResponse;
import com.tweetapp.TweetApp.service.UserService;

import lombok.extern.slf4j.Slf4j;

/******This class use to Convert DTO object to Domain Object******/
@Slf4j
@Component
public class UserMapper {

	@Autowired
	private UserService userService;
	private ModelMapper modelMapper;

	public UserMapper(ModelMapper mapper) {
		this.modelMapper = mapper;
	}

	public List<UserResponse> getAllUsers() {
		log.info("inside getAllUsers method of UserMapper");
		List<User> users = userService.getAllUsers();
		List<UserResponse> usersReponse = users.stream().map(user -> modelMapper.map(user, UserResponse.class))
				.collect(Collectors.toList());
		log.info("Converting Users list to UserReponse List =>{}", usersReponse);
		return usersReponse;
	}

	public List<UserResponse> getUsers(String username) {
		log.info("inside getUser method of UserMapper");
		List<User> users = userService.getUsers(username);
		List<UserResponse> usersReponse = users.stream().map(user -> modelMapper.map(user, UserResponse.class))
				.collect(Collectors.toList());
		log.info("Converting Users list to UserReponse List =>{}", usersReponse);
		return usersReponse;
	}
}
