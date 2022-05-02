package com.tweetapp.TweetApp.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

import com.tweetapp.TweetApp.domain.Tweet;
import com.tweetapp.TweetApp.domain.TweetLikes;
import com.tweetapp.TweetApp.domain.TweetReply;
import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.dto.tweet.TweetResponse;
import com.tweetapp.TweetApp.exception.TweetNotFoundException;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.repository.LikeRepository;
import com.tweetapp.TweetApp.repository.ReplyRepository;
import com.tweetapp.TweetApp.repository.TweetRepository;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.service.impl.TweetServiceImpl;

public class TweetServiceTest {

	@Mock
	private TweetRepository tweetRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ReplyRepository replyRepository;
	@Mock
	private LikeRepository likeRepository;
	@InjectMocks
	private TweetServiceImpl tweetService;

	TweetRequest tweetRequest;
	TweetResponse tweetResponse;
	User user;
	Tweet tweet;

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
		user = new User();
		user.setConfirmPassword("123");
		user.setContactNumber("9849364738");
		user.setEmail("a11@gmail.com");
		user.setFirstName("first");
		user.setLastName("last");
		user.setLoginId("a@123");
		user.setPassword("123");
		tweet = new Tweet();
		tweet.setId("12345");
		tweet.setLikes(new ArrayList<>());
		tweet.setPostTime(LocalDateTime.now());
		tweet.setReplies(new ArrayList<>());
		tweet.setTag("tag");
		tweet.setTweetText("tweet text");
		tweet.setUser(user);
	}

	@Test
	@DirtiesContext
	public void postTweetTest() {

		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(tweetRepository.insert(tweet)).thenReturn(tweet);
		String actual = tweetService.postTweet("a@123", tweet);
		assertEquals("Tweet is posted!!", actual);
	}

	@Test
	public void postTweetUserIsNotPresentTest() {

		Mockito.when(userRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.empty());
		UserNotFoundException actual = assertThrows(UserNotFoundException.class,
				() -> tweetService.postTweet("a@123", tweet));
		assertEquals("User with a@123 is not found", actual.getUserNotFound());
	}

	@Test
	public void getAllTweetsTest() {
		List<Tweet> allTweets = new ArrayList<Tweet>();
		allTweets.add(tweet);
		Mockito.when(tweetRepository.findAll()).thenReturn(allTweets);
		List<Tweet> actual = tweetService.getAllTweets();
		assertEquals(allTweets, actual);
	}

	@Test
	public void getAllTweetsIsEmptyTest() {
		List<Tweet> allTweets = new ArrayList<Tweet>();
		Mockito.when(tweetRepository.findAll()).thenReturn(allTweets);
		TweetNotFoundException actual = assertThrows(TweetNotFoundException.class, () -> tweetService.getAllTweets());
		assertEquals("No Tweets present", actual.getTweetNotFound());
	}

	@Test
	public void getAllTweetsByUserTest() {
		List<Tweet> allTweets = new ArrayList<Tweet>();
		allTweets.add(tweet);
		Mockito.when(userRepository.findByLoginId("a@123")).thenReturn(Optional.of(user));
		Mockito.when(tweetRepository.findAllByUser(user)).thenReturn(allTweets);
		List<Tweet> actual = tweetService.getAllTweetsByUser("a@123");
		assertEquals(allTweets, actual);
	}

	@Test
	public void getAllTweetsByUserIsEmptyTest() {
		List<Tweet> allTweets = new ArrayList<Tweet>();
		Mockito.when(userRepository.findByLoginId("a@123")).thenReturn(Optional.of(user));
		Mockito.when(tweetRepository.findAllByUser(user)).thenReturn(allTweets);
		TweetNotFoundException actual = assertThrows(TweetNotFoundException.class,
				() -> tweetService.getAllTweetsByUser("a@123"));
		assertEquals("No Tweets present for particular user", actual.getTweetNotFound());
	}

	@Test
	public void getAllTweetsByUserINotPresentTest() {
		Mockito.when(userRepository.findByLoginId("a@123")).thenReturn(Optional.empty());
		UserNotFoundException actual = assertThrows(UserNotFoundException.class,
				() -> tweetService.getAllTweetsByUser("a@123"));
		assertEquals("User with a@123 is not found", actual.getUserNotFound());
	}

	@Test
	@DirtiesContext
	public void updateTweetTest() {
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.of(tweet));
		Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
		String actual = tweetService.updateTweet("12345", tweetRequest);
		assertEquals("tweet is updated !!", actual);
	}

	@Test
	public void updateTweetIsEmptyTest() {
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		TweetNotFoundException actual = assertThrows(TweetNotFoundException.class,
				() -> tweetService.updateTweet("12345", tweetRequest));
		assertEquals("Tweet with give id 12345 is not found", actual.getTweetNotFound());
	}

	@Test
	@DirtiesContext
	public void deleteTweetTest() {
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.of(tweet));
		String actual = tweetService.deleteTweet("12345");
		assertEquals("tweet is deleted !!", actual);
	}

	@Test
	public void deleteTweetTweetNotFoundTest() {
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		TweetNotFoundException actual = assertThrows(TweetNotFoundException.class,
				() -> tweetService.deleteTweet("12345"));
		assertEquals("Tweet with give id 12345 is not found", actual.getTweetNotFound());
	}

	@Test
	@DirtiesContext
	public void addReplyTest() {
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.of(tweet));
		TweetReply tweetReply = new TweetReply();
		tweetReply.setPostTime(LocalDateTime.now());
		tweetReply.setReplyText(tweetRequest.getTweetText());
		tweetReply.setTag(tweetRequest.getTag());
		tweetReply.setUsername("a@123");
		Mockito.when(replyRepository.insert(tweetReply)).thenReturn(tweetReply);
		Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
		String actual = tweetService.addReply("a@123", "12345", tweetRequest);
		assertEquals("reply is added", actual);
	}

	@Test
	public void addReplyTestTweetNotFoundTest() {
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		TweetNotFoundException actual = assertThrows(TweetNotFoundException.class,
				() -> tweetService.addReply("a@123", "12345", tweetRequest));
		assertEquals("Tweet with give id 12345 is not found", actual.getTweetNotFound());
	}

	@Test
	@DirtiesContext
	public void addLikeTest() {
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.of(tweet));
		TweetLikes tweetLike = new TweetLikes();
		tweetLike.setLikeDate(LocalDateTime.now());
		tweetLike.setUserName("a@123");
		Mockito.when(likeRepository.save(tweetLike)).thenReturn(tweetLike);
		Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
		String actual = tweetService.addLike("a@123", "12345");
		assertEquals("like is added", actual);
	}

	@Test
	@DirtiesContext
	public void addLikeSizeNotZeroTest() {
		TweetLikes tweetLike1 = new TweetLikes();
		tweetLike1.setLikeDate(LocalDateTime.now());
		tweetLike1.setUserName("w@123");
		TweetLikes tweetLike2 = new TweetLikes();
		tweetLike2.setLikeDate(LocalDateTime.now());
		tweetLike2.setUserName("a@123");
		tweet.getLikes().add(tweetLike1);
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.of(tweet));
		TweetLikes tweetLike = new TweetLikes();
		tweetLike.setLikeDate(LocalDateTime.now());
		tweetLike.setUserName("a@123");
		Mockito.when(likeRepository.save(tweetLike)).thenReturn(tweetLike);
		Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
		String actual = tweetService.addLike("a@123", "12345");
		assertEquals("like is added", actual);
	}

	@Test
	@DirtiesContext
	public void addLikeAlreadyPresentTest() {
		TweetLikes tweetLike1 = new TweetLikes();
		tweetLike1.setLikeDate(LocalDateTime.now());
		tweetLike1.setUserName("w@123");
		TweetLikes tweetLike2 = new TweetLikes();
		tweetLike2.setLikeDate(LocalDateTime.now());
		tweetLike2.setUserName("a@123");
		tweet.getLikes().add(tweetLike1);
		tweet.getLikes().add(tweetLike2);
		Mockito.when(tweetRepository.findById(Mockito.anyString())).thenReturn(Optional.of(tweet));
		TweetLikes tweetLike = new TweetLikes();
		tweetLike.setLikeDate(LocalDateTime.now());
		tweetLike.setUserName("a@123");
		Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
		String actual = tweetService.addLike("a@123", "12345");
		assertEquals("like is removed", actual);
	}
}
