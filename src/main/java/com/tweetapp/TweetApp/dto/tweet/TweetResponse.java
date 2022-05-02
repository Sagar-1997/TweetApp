package com.tweetapp.TweetApp.dto.tweet;

import java.time.LocalDateTime;
import java.util.List;

import com.tweetapp.TweetApp.domain.TweetLikes;
import com.tweetapp.TweetApp.domain.TweetReply;

import lombok.Data;

@Data
public class TweetResponse {

	private String id;
	private String tweetText;
	private String tag;
	private String username;
	private LocalDateTime postTime;
	private List<TweetLikes> likes;
	private List<TweetReply> replies;
}
