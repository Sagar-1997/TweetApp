package com.tweetapp.TweetApp.dto.auth;

import java.util.Date;

import lombok.Data;

@Data
public class AuthResponse {
	private String username;
	private String Token;
	private Date expirationTime;
}
