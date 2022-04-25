package com.tweetapp.TweetApp.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class TokenException extends AuthenticationException{
	private static final long serialVersionUID = 1L;
	public TokenException(String exception) {
		super(exception);
	}
}
