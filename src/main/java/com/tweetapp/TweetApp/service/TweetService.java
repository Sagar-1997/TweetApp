package com.tweetapp.TweetApp.service;

import java.util.List;

import com.tweetapp.TweetApp.domain.Tweet;

public interface TweetService {
      public String postTweet(String username,Tweet tweet);

      public List<Tweet> getAllTweets();
}
