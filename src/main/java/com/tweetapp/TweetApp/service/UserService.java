package com.tweetapp.TweetApp.service;

import java.util.List;

import com.tweetapp.TweetApp.domain.User;

public interface UserService {
	
	public List<User> getAllUsers();
	public User getUser(String username);

}
