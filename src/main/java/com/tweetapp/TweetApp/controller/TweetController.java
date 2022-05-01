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
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.dto.tweet.TweetResponse;
import com.tweetapp.TweetApp.mapper.TweetMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TweetController {
	private TweetMapper tweetMapper;

	public TweetController(TweetMapper mapper) {
		this.tweetMapper = mapper;
	}

	/* Getting all tweets */
	@GetMapping("/all")
	public ResponseEntity<List<TweetResponse>> getAllTweets() {
		log.info("inside getAllTweets method of TweetController");
		return ResponseEntity.ok().body(tweetMapper.getAllTweets());
	}

	/* Getting all tweets of particular user */
	@GetMapping("/{username}")
	public ResponseEntity<List<TweetResponse>> getAllTweetsByUser(@PathVariable("username") String username) {
		log.info("inside getAllTweetsByUser method of TweetController");
		return ResponseEntity.ok().body(tweetMapper.getAllTweetsByUser(username));
	}

	/* create new tweet */
	@PostMapping("/{username}/add")
	public ResponseEntity<String> postTweet(@PathVariable("username") String username,
			@RequestBody @Valid TweetRequest tweetrequest, BindingResult bindingResult) {
		log.info("inside postTweet method of TweetController");
		return ResponseEntity.ok().body(tweetMapper.postTweet(username, tweetrequest, bindingResult));
	}

	/* reply to tweet */
	@PostMapping("/{username}/reply/{id}")
	public ResponseEntity<String> addReply(@PathVariable("username") String username, @PathVariable("id") String id,
			@RequestBody @Valid TweetRequest tweetRequest, BindingResult bindingResult) {
		log.info("inside addReply method of TweetController");
		return ResponseEntity.ok().body(tweetMapper.addReply(username, id, tweetRequest, bindingResult));
	}

	/* update tweet */
	@PutMapping("/{username}/update/{id}")
	public ResponseEntity<String> updateTweet(@PathVariable("id") String id,
			@RequestBody @Valid TweetRequest tweetRequest, BindingResult bindingResult) {
		log.info("inside updateTweet method of TweetController");
		return ResponseEntity.ok().body(tweetMapper.updateTweet(id, tweetRequest, bindingResult));
	}

	/* like a tweet */
	@PutMapping("/{username}/like/{id}")
	public ResponseEntity<String> addLike(@PathVariable("username") String username, @PathVariable("id") String id) {
		log.info("inside addLike method of TweetController");
		return ResponseEntity.ok().body(tweetMapper.addLike(username, id));
	}

	/* delete a tweet */
	@DeleteMapping("/{username}/delete/{id}")
	public ResponseEntity<String> deleteTweet(@PathVariable("id") String id) {
		log.info("inside deleteTweet method of TweetController");
		return ResponseEntity.ok().body(tweetMapper.deleteTweet(id));
	}
}
