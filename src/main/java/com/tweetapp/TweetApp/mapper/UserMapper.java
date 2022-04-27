package com.tweetapp.TweetApp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.user.UserResponse;
import com.tweetapp.TweetApp.service.UserService;

@Component
public class UserMapper {

	@Autowired
	private UserService userService;
	private ModelMapper modelMapper;
	public UserMapper(ModelMapper mapper) {
		this.modelMapper=mapper;
	}
	
	public List<UserResponse> getAllUsers()
	{
		List<User> users = userService.getAllUsers();
		List<UserResponse> usersReponse = users.stream().map(user-> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
		return usersReponse;
	}
	
	public List<UserResponse> getUser(String username)
	{
		List<User> users = userService.getUser(username);
		List<UserResponse> usersReponse = users.stream().map(user-> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
		return usersReponse;
	}
}
