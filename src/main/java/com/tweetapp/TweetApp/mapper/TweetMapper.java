package com.tweetapp.TweetApp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.tweetapp.TweetApp.domain.Tweet;
import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.dto.tweet.TweetResponse;
import com.tweetapp.TweetApp.dto.user.UserResponse;
import com.tweetapp.TweetApp.exception.InputFeildException;
import com.tweetapp.TweetApp.service.TweetService;

@Component
public class TweetMapper {
	@Autowired
	private TweetService tweetService;
	private ModelMapper modelMapper;

	public TweetMapper(ModelMapper mapper) {
		this.modelMapper = mapper;
	}

	public String postTweet(String username, TweetRequest tweetRequest, BindingResult bindResult) {
		if (bindResult.hasErrors()) {
			throw new InputFeildException(bindResult);
		}
		Tweet tweet = modelMapper.map(tweetRequest, Tweet.class);
		return tweetService.postTweet(username, tweet);
	}

	public List<TweetResponse> getAllTweets() {
		List<Tweet> allTweets = tweetService.getAllTweets();
		List<TweetResponse> allTweetsResponse = allTweets.stream().map(tweet -> {
			TweetResponse tweetResponse = modelMapper.map(tweet, TweetResponse.class);
			tweetResponse.setUsername(tweet.getUser().getLoginId());
			return tweetResponse;

		}).collect(Collectors.toList());
		return allTweetsResponse;
	}

}
