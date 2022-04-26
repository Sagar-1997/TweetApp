package com.tweetapp.TweetApp.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tweet_Likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetLike {
	
	@Id
	private String id;
    private Date likeDate;
    private String userName;
    
}
