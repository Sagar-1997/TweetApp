package com.tweetapp.TweetApp.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "tweet_Likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetLikes {

	@Id
	@Setter(value = AccessLevel.NONE)
	private String id;
	private Date likeDate;
	private String userName;
	@JsonIgnore
	private String tweetId;

}
