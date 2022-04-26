package com.tweetapp.TweetApp.dto.tweet;

import java.util.Date;
import java.util.List;

import com.tweetapp.TweetApp.domain.TweetReply;

import lombok.Data;

@Data
public class TweetResponse {

	private String tweetText;
	private String tag;
	private String username;
	private Date postTime;
	private int likes;
	private List<TweetReply> replies;
}
