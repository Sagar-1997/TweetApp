package com.tweetapp.TweetApp.dto.auth;

import lombok.Data;

@Data
public class AuthRequest {

	private String username;
	private String password;
}
