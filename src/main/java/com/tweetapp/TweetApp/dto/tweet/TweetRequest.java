package com.tweetapp.TweetApp.dto.tweet;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class TweetRequest {

	@Length(min=1,max = 144)
	private String tweetText;
    @Length(min = 0,max = 50)
	private String tag;
}
