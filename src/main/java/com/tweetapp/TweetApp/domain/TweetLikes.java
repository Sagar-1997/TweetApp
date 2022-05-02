package com.tweetapp.TweetApp.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private LocalDateTime likeDate;
	private String userName;

}
