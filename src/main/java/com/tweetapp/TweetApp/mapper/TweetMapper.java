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
import com.tweetapp.TweetApp.exception.InputFeildException;
import com.tweetapp.TweetApp.service.TweetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TweetMapper {
	@Autowired
	private TweetService tweetService;
	private ModelMapper modelMapper;

	public TweetMapper(ModelMapper mapper) {
		this.modelMapper = mapper;
	}

	public String postTweet(String username, TweetRequest tweetRequest, BindingResult bindResult) {
		log.info("inside postTweet method of TweetMapper");
		if (bindResult.hasErrors()) {
			log.info("Input Feild Exception");
			throw new InputFeildException(bindResult);
		}
		Tweet tweet = modelMapper.map(tweetRequest, Tweet.class);
		log.info("Converting TweetRequest to Tweet => {}", tweet);
		return tweetService.postTweet(username, tweet);
	}

	public List<TweetResponse> getAllTweets() {
		log.info("inside getAllTweets method of TweetMapper");
		List<Tweet> allTweets = tweetService.getAllTweets();
		List<TweetResponse> allTweetsResponse = allTweets.stream().map(tweet -> {
			TweetResponse tweetResponse = modelMapper.map(tweet, TweetResponse.class);
			tweetResponse.setUsername(tweet.getUser().getLoginId());
			return tweetResponse;

		}).collect(Collectors.toList());
		log.info("Converting Tweet List to TweetReponse List => {}", allTweetsResponse);
		return allTweetsResponse;
	}

	public List<TweetResponse> getAllTweetsByUser(String username) {
		log.info("inside getAllTweetsByUser method of TweetMapper");
		List<Tweet> tweets = tweetService.getAllTweetsByUser(username);
		List<TweetResponse> allTweetsResponse = tweets.stream().map(tweet -> {
			TweetResponse tweetResponse = modelMapper.map(tweet, TweetResponse.class);
			tweetResponse.setUsername(tweet.getUser().getLoginId());
			return tweetResponse;
		}).collect(Collectors.toList());
		log.info("Converting Tweet List to TweetReponse List => {}", allTweetsResponse);
		return allTweetsResponse;
	}

	public String updateTweet(String id, TweetRequest tweetRequest, BindingResult bindingResult) {
		log.info("inside updateTweet method of TweetMapper");
		if (bindingResult.hasErrors()) {
			log.info("Input Feild Exception");
			throw new InputFeildException(bindingResult);
		}
		return tweetService.updateTweet(id, tweetRequest);
	}

	public String deleteTweet(String id) {
		log.info("inside deleteTweet method of TweetMapper");
		return tweetService.deleteTweet(id);
	}

	public String addReply(String username, String id, TweetRequest tweetRequest, BindingResult bindingResult) {
		log.info("inside addReply method of TweetMapper");
		if (bindingResult.hasErrors()) {
			log.info("Input Feild Exception");
			throw new InputFeildException(bindingResult);
		}
		return tweetService.addReply(username, id, tweetRequest);
	}

	public String addLike(String username, String id) {
		log.info("inside addLike method of TweetMapper");
		return tweetService.addLike(username, id);
	}

}