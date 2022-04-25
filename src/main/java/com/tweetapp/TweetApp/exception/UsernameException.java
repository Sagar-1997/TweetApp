package com.tweetapp.TweetApp.exception;

import lombok.Getter;

@Getter
public class UsernameException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String usernameError;
	public UsernameException(String errorMessage) {
		this.usernameError=errorMessage;
	}
}
