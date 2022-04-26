package com.tweetapp.TweetApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.dto.tweet.TweetResponse;
import com.tweetapp.TweetApp.mapper.TweetMapper;

@RestController
@RequestMapping("/api/v1.0/tweets/")
public class TweetController {
	private TweetMapper tweetMapper;

	public TweetController(TweetMapper mapper) {
		this.tweetMapper = mapper;
	}

	@GetMapping("/all")
	public ResponseEntity<List<TweetResponse>> getAllTweets() {
		return ResponseEntity.ok().body(tweetMapper.getAllTweets());
	}

	@PostMapping("/{username}/add")
	public ResponseEntity<String> postTweet(@PathVariable("username") String username,
			@RequestBody @Valid TweetRequest tweetrequest, BindingResult bindingResult) {
		return ResponseEntity.ok().body(tweetMapper.postTweet(username, tweetrequest, bindingResult));
	}

	@GetMapping("/{username}")
	public ResponseEntity<List<TweetResponse>> getAllTweetsByUser(@PathVariable("username") String username) {
		return ResponseEntity.ok().body(tweetMapper.getAllTweetsByUser(username));
	}

	@PutMapping("/{username}/update/{id}")
	public ResponseEntity<String> updateTweet(@PathVariable("id") String id,
			@RequestBody @Valid TweetRequest tweetRequest, BindingResult bindingResult) {
		return ResponseEntity.ok().body(tweetMapper.updateTweet(id, tweetRequest, bindingResult));
	}

	@PostMapping("/{username}/reply/{id}")
	public ResponseEntity<String> addReply(@PathVariable("username") String username, @PathVariable("id") String id,
			@RequestBody @Valid TweetRequest tweetRequest, BindingResult bindingResult) {
		return ResponseEntity.ok().body(tweetMapper.addReply(username, id, tweetRequest, bindingResult));
	}

	@DeleteMapping("/{username}/delete/{id}")
	public ResponseEntity<String> deleteTweet(@PathVariable("id") String id) {
		return ResponseEntity.ok().body(tweetMapper.deleteTweet(id));
	}
}
