package com.tweetapp.TweetApp.service;

import com.tweetapp.TweetApp.domain.User;

public interface AuthenticationService {

	String registerUser(User userEntity);
	String login(String username,String password);
	String forgotPassword(String password, String confirmPassword, String string);
}
