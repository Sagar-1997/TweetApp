package com.tweetapp.TweetApp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Tweet")
public class Tweet {
	@Id
	@Setter(value = AccessLevel.NONE)
	private String Id;
	private String tweetText;
	private String tag;
	@DBRef
	private User user;
	private Date postTime;
	private int likes;
	@DBRef
	@Setter(value = AccessLevel.NONE)
	private List<TweetReply> replies= new ArrayList<TweetReply>();
	public void addReply(TweetReply reply)
	{
		replies.add(reply);
	}
    public void removeReply(TweetReply reply)
    {
    	replies.remove(reply);
    }
}
