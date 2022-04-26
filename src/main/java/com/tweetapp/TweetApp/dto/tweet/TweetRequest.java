package com.tweetapp.TweetApp.dto.tweet;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class TweetRequest {
	
	@NotNull(message = "tweet text should not be Empty")
	@Length(min=1,max = 144,message = "tweet text length should not be zero or exceeds 144 characters")
	private String tweetText;
    @Length(min = 0,max = 50,message = "tag length should not exceeds 50 characters ")
	private String tag;
}
