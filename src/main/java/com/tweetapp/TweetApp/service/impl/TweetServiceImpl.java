package com.tweetapp.TweetApp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.TweetApp.domain.Tweet;
import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.tweet.TweetResponse;
import com.tweetapp.TweetApp.repository.TweetRepository;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.service.TweetService;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	private TweetRepository tweetRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	public String postTweet(String username,Tweet tweet) {
		User user = userRepository.findByLoginId(username).get();
		tweet.setPostTime(new Date());
		tweet.setUser(user);
		tweetRepository.insert(tweet);
		return "Tweet is been posted!!";
	}
	@Override
	public List<Tweet> getAllTweets() {
		List<Tweet> tweetList = tweetRepository.findAll();
		return tweetList;
	}

	
}
