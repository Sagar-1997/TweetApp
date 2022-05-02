package com.tweetapp.TweetApp.exception;

import lombok.Getter;

@Getter
public class LoginFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String loginFailed;

	public LoginFailedException(String exception) {
		this.loginFailed = exception;
	}
}
