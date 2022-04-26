package com.tweetapp.TweetApp.dto.tweet;

import java.util.Date;
import java.util.List;

import com.tweetapp.TweetApp.domain.TweetLike;
import com.tweetapp.TweetApp.domain.TweetReply;

import lombok.Data;

@Data
public class TweetResponse {

	private String id;
	private String tweetText;
	private String tag;
	private String username;
	private Date postTime;
	private List<TweetLike> likes;
	private List<TweetReply> replies;
}
