package com.tweetapp.TweetApp.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String userNotFound;

	public UserNotFoundException(String usernotfound) {
		this.userNotFound = usernotfound;
	}
}
