package com.tweetapp.TweetApp.exception;

import lombok.Getter;

@Getter
public class TweetNotFoundException extends RuntimeException {

	private String tweetNotFound;
	public TweetNotFoundException(String exception) {
		this.tweetNotFound=exception;
	}
}
