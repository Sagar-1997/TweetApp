package com.tweetapp.TweetApp.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Tweet")
public class Tweet {
	@Id
	private String id;
	private String tweetText;
	private String tag;
	@DBRef
	private User user;
	private LocalDateTime postTime;
	@DBRef
	private List<TweetLikes> likes = new ArrayList<TweetLikes>();
	@DBRef
	private List<TweetReply> replies = new ArrayList<TweetReply>();
}
