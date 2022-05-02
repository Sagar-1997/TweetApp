package com.tweetapp.TweetApp.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import com.tweetapp.TweetApp.domain.Tweet;
import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.dto.tweet.TweetResponse;
import com.tweetapp.TweetApp.service.TweetService;

@SpringBootTest
public class TweetMapperTest {

	@InjectMocks
	private TweetMapper tweetMapper;

	@Mock
	private TweetService tweetService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private BindingResult bindResult;

	TweetRequest tweetRequest;
	TweetResponse tweetResponse;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
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
	public void postTweetTest() {
		String username = "a@123";
		Tweet tweet = new Tweet();
		tweet.setId("12345");
		tweet.setLikes(new ArrayList<>());
		tweet.setPostTime(LocalDateTime.now());
		tweet.setReplies(new ArrayList<>());
		tweet.setTag("tag");
		tweet.setTweetText("tweet text");
		tweet.setUser(new User());
		Mockito.when(modelMapper.map(tweetRequest, Tweet.class)).thenReturn(tweet);
		Mockito.when(tweetService.postTweet(username, tweet)).thenReturn("posted");
		String actual = tweetMapper.postTweet(username, tweetRequest, bindResult);
		assertEquals("posted", actual);
	}

	@Test
	public void getAllTweetsTest() {
		ArrayList<TweetResponse> alltweet = new ArrayList<TweetResponse>();
		User user = new User();
		user.setLoginId("a@123");
		alltweet.add(tweetResponse);
		Tweet tweet = new Tweet();
		tweet.setId("12345");
		tweet.setLikes(new ArrayList<>());
		tweet.setPostTime(LocalDateTime.now());
		tweet.setReplies(new ArrayList<>());
		tweet.setTag("tag");
		tweet.setTweetText("tweet text");
		tweet.setUser(user);
		ArrayList<Tweet> alltweetEntity = new ArrayList<Tweet>();
		alltweetEntity.add(tweet);
		Mockito.when(modelMapper.map(tweet, TweetResponse.class)).thenReturn(tweetResponse);
		Mockito.when(tweetService.getAllTweets()).thenReturn(alltweetEntity);
		List<TweetResponse> actual = tweetMapper.getAllTweets();
		assertEquals(alltweet, actual);
	}

	@Test
	public void getAllTweetsByUserTest() {
		String username = "a@123";
		ArrayList<TweetResponse> alltweet = new ArrayList<TweetResponse>();
		User user = new User();
		user.setLoginId("a@123");
		alltweet.add(tweetResponse);
		Tweet tweet = new Tweet();
		tweet.setId("12345");
		tweet.setLikes(new ArrayList<>());
		tweet.setPostTime(LocalDateTime.now());
		tweet.setReplies(new ArrayList<>());
		tweet.setTag("tag");
		tweet.setTweetText("tweet text");
		tweet.setUser(user);
		ArrayList<Tweet> alltweetEntity = new ArrayList<Tweet>();
		alltweetEntity.add(tweet);
		Mockito.when(modelMapper.map(tweet, TweetResponse.class)).thenReturn(tweetResponse);
		Mockito.when(tweetService.getAllTweetsByUser(username)).thenReturn(alltweetEntity);
		List<TweetResponse> actual = tweetMapper.getAllTweetsByUser(username);
		assertEquals(alltweet, actual);
	}

	@Test
	public void updateTweetTest() {
		String tweetid = "12345";
		Mockito.when(tweetService.updateTweet(tweetid, tweetRequest)).thenReturn("Updated");
		String actual = tweetMapper.updateTweet(tweetid, tweetRequest, bindResult);
		assertEquals("Updated", actual);
	}

	@Test
	public void deleteTweetTest() {
		String tweetId = "12345";
		Mockito.when(tweetService.deleteTweet(tweetId)).thenReturn("Deleted");
		String actual = tweetMapper.deleteTweet(tweetId);
		assertEquals("Deleted", actual);
	}

	@Test
	public void addReplyTest() {
		String tweetId = "12345";
		String username = "A@123";
		Mockito.when(tweetService.addReply(username, tweetId, tweetRequest)).thenReturn("Reply Added");
		String actual = tweetMapper.addReply(username, tweetId, tweetRequest, bindResult);
		assertEquals("Reply Added", actual);
	}

	@Test
	public void addLikeTest() {
		String tweetId = "12345";
		String username = "a@123";
		Mockito.when(tweetService.addLike(username, tweetId)).thenReturn("Like Added");
		String actual = tweetMapper.addLike(username, tweetId);
		assertEquals("Like Added", actual);
	}
}
