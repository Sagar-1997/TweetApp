package com.tweetapp.TweetApp.exception;

import lombok.Getter;

@Getter
public class PasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String passwordError;

	public PasswordException(String errorMessage) {
		this.passwordError = errorMessage;
	}
}
