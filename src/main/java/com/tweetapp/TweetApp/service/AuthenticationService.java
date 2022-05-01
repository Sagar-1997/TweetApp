package com.tweetapp.TweetApp.service;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.auth.AuthResponse;

public interface AuthenticationService {

	String registerUser(User userEntity);

	AuthResponse login(String username, String password);

	String forgotPassword(String password, String confirmPassword, String string);
}
