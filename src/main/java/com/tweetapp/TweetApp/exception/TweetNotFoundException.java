package com.tweetapp.TweetApp.exception;

import lombok.Getter;

@Getter
public class TweetNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String tweetNotFound;

	public TweetNotFoundException(String exception) {
		this.tweetNotFound = exception;
	}
}
