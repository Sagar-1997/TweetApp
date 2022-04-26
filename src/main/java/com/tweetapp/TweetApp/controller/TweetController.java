package com.tweetapp.TweetApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.dto.tweet.TweetResponse;
import com.tweetapp.TweetApp.dto.user.UserResponse;
import com.tweetapp.TweetApp.mapper.TweetMapper;

@RestController
@RequestMapping("/api/v1.0/tweets/")
public class TweetController {
    private TweetMapper tweetMapper;
    public TweetController(TweetMapper mapper) {
		this.tweetMapper = mapper;
	}
    @GetMapping("/all")
    public ResponseEntity<List<TweetResponse>> getAllTweets()
    {
    	return ResponseEntity.ok().body(tweetMapper.getAllTweets());
    }
    @PostMapping("/{username}/add")
	public ResponseEntity<String> postTweet(@PathVariable("username") String username,
			@RequestBody @Valid TweetRequest tweetrequest, BindingResult bindingResult)
    {
    	return ResponseEntity.ok().body(tweetMapper.postTweet(username,tweetrequest,bindingResult));
    }
}
