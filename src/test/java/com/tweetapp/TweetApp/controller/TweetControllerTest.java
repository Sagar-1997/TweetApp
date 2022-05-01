package com.tweetapp.TweetApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.dto.tweet.TweetResponse;
import com.tweetapp.TweetApp.mapper.TweetMapper;

@SpringBootTest
public class TweetControllerTest {

	@InjectMocks
	private TweetController tweetController;

	@Mock
	private TweetMapper tweetMapper;
	@Mock
	private BindingResult bindResult;
	
	TweetRequest tweetRequest;
	TweetResponse tweetResponse;

	@BeforeEach
	public void setUp() {
		tweetRequest = new TweetRequest();
		tweetRequest.setTag("tag");
		tweetRequest.setTweetText("tweet text");
		tweetResponse = new TweetResponse();
		tweetResponse.setId("123");
		tweetResponse.setLikes(new ArrayList<>());
		tweetResponse.setPostTime(LocalDateTime.now());
		tweetResponse.setReplies(new ArrayList<>());
		tweetResponse.setTag("tag");
		tweetResponse.setTweetText("tweet text");
		tweetResponse.setUsername("a@123");
	}

	@Test
	public void getAllTweetsTest() {
		List<TweetResponse> allTweet = new ArrayList<TweetResponse>();
		allTweet.add(tweetResponse);
		Mockito.when(tweetMapper.getAllTweets()).thenReturn(allTweet);
		ResponseEntity<List<TweetResponse>> actual = tweetController.getAllTweets();
		assertEquals(allTweet, actual.getBody());
	}
	
	@Test
	public void getAllTweetsByUserTest() {
		String username="a@123";
		List<TweetResponse> allTweet = new ArrayList<TweetResponse>();
		allTweet.add(tweetResponse);
		Mockito.when(tweetMapper.getAllTweetsByUser(username)).thenReturn(allTweet);
		ResponseEntity<List<TweetResponse>> actual = tweetController.getAllTweetsByUser(username);
		assertEquals(allTweet, actual.getBody());
	}
	@Test
	public void postTweetTest()
	{
		String username="a@123";
		Mockito.when(tweetMapper.postTweet(username, tweetRequest, bindResult)).thenReturn("tweet posted");
		ResponseEntity<String> actual = tweetController.postTweet(username, tweetRequest, bindResult);
		assertEquals("tweet posted", actual.getBody());
	}
	@Test
	public void addReplyTest()
	{
		Mockito.when(tweetMapper.addReply("a@123", "928376", tweetRequest, bindResult)).thenReturn("reply added");
		ResponseEntity<String> actual = tweetController.addReply("a@123", "928376", tweetRequest, bindResult);
		assertEquals("reply added", actual.getBody());
	}
	
	@Test
	public void updateTweetTest()
	{
		Mockito.when(tweetMapper.updateTweet( "928376", tweetRequest, bindResult)).thenReturn("Tweet Updated");
		ResponseEntity<String> actual = tweetController.updateTweet("928376", tweetRequest, bindResult);
		assertEquals("Tweet Updated", actual.getBody());
	}
	
	@Test
	public void addLikeTest()
	{
		Mockito.when(tweetMapper.addLike("a@123", "928376")).thenReturn("like added");
		ResponseEntity<String> actual = tweetController.addLike("a@123", "928376");
		assertEquals("like added", actual.getBody());
	}
	
	@Test
	public void deleteTweet()
	{
		Mockito.when(tweetMapper.deleteTweet("12345")).thenReturn("tweet delete");
		ResponseEntity<String> actual = tweetController.deleteTweet("12345");
		assertEquals("tweet delete", actual.getBody());
	}
}
