package com.tweetapp.TweetApp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.TweetApp.domain.Tweet;
import com.tweetapp.TweetApp.domain.TweetLikes;
import com.tweetapp.TweetApp.domain.TweetReply;
import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.exception.TweetNotFoundException;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.repository.LikeRepository;
import com.tweetapp.TweetApp.repository.ReplyRepository;
import com.tweetapp.TweetApp.repository.TweetRepository;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.service.TweetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	private TweetRepository tweetRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReplyRepository replyRepsitory;
	@Autowired
	private LikeRepository likeRepository;

	@Override
	public String postTweet(String username, Tweet tweet) {
		log.info("Inside postTweet method of TweetServiceImpl");
		User user = userRepository.findByLoginId(username)
				.orElseThrow(() -> new UserNotFoundException("User with " + username + "is not found"));
		tweet.setPostTime(LocalDateTime.now());
		tweet.setUser(user);
		Tweet insertedTweet = tweetRepository.insert(tweet);
		log.info("New tweet value =>{}", insertedTweet);
		return "Tweet is been posted!!";
	}

	@Override
	public List<Tweet> getAllTweets() {
		log.info("Inside getAllTweets method of TweetServiceImpl");
		List<Tweet> tweetList = tweetRepository.findAll();
		log.info("List of All Tweets => {}", tweetList);
		if (tweetList.isEmpty() || tweetList == null) {
			log.info("Tweet list is emply");
			throw new TweetNotFoundException("No Tweets present");
		}
		return tweetList;
	}

	@Override
	public List<Tweet> getAllTweetsByUser(String username) {
		log.info("Inside getAllTweetsByUser method of TweetServiceImpl");
		User user = userRepository.findByLoginId(username).orElseThrow(() -> new UserNotFoundException("User with " + username + "is not found"));
		List<Tweet> findAllByUser = tweetRepository.findAllByUser(user);
		if(findAllByUser.size()==0 || findAllByUser==null)
		{
			log.info("Tweet list is emply");
			throw new TweetNotFoundException("No Tweets present for particular user");
		}
		return findAllByUser;
	}

	@Override
	public String updateTweet(String id, TweetRequest tweetRequest) {
		log.info("Inside updateTweet method of TweetServiceImpl");
		Tweet tweet = tweetRepository.findById(id)
				.orElseThrow(() -> new TweetNotFoundException("Tweet with give id " + id + " is not found"));
		tweet.setTweetText(tweetRequest.getTweetText());
		tweet.setPostTime(LocalDateTime.now());
		tweet.setTag(tweetRequest.getTag());
		Tweet updatedTweet = tweetRepository.save(tweet);
		log.info("Tweet after update => {}", updatedTweet);
		return "post for updates !!";
	}

	@Override
	public String deleteTweet(String id) {
		log.info("Inside deleteTweet method of TweetServiceImpl");
		Tweet tweet = tweetRepository.findById(id)
				.orElseThrow(() -> new TweetNotFoundException("Tweet with give id " + id + " is not found"));
		tweetRepository.delete(tweet);
		log.info("Tweet is delete with id " + id);
		return "post is deleted";
	}

	@Override
	public String addReply(String username, String id, TweetRequest tweetRequest) {
		log.info("Inside addReply method of TweetServiceImpl");
		Tweet tweet = tweetRepository.findById(id)
				.orElseThrow(() -> new TweetNotFoundException("Tweet with give id " + id + " is not found"));
		TweetReply tweetReply = new TweetReply();
		tweetReply.setPostTime(LocalDateTime.now());
		tweetReply.setReplyText(tweetRequest.getTweetText());
		tweetReply.setTag(tweetRequest.getTag());
		tweetReply.setUsername(username);
		replyRepsitory.insert(tweetReply);
		tweet.getReplies().add(tweetReply);
		Tweet tweetAdded = tweetRepository.save(tweet);
		log.info("Tweet Reply => {}", tweetReply);
		log.info("Tweet Reply added to tweet => {}", tweetAdded);
		return "reply is added";
	}

	@Override
	public String addLike(String username, String id) {
		log.info("Inside addLike method of TweetServiceImpl");
		String likeMessage = "";
		Tweet tweet = tweetRepository.findById(id)
				.orElseThrow(() -> new TweetNotFoundException("Tweet with give id " + id + " is not found"));
		TweetLikes tweetLike = new TweetLikes();
		tweetLike.setLikeDate(LocalDateTime.now());
		tweetLike.setUserName(username);
		if (tweet.getLikes().size() > 0) {
			TweetLikes tweetlike = null;
			for (int i = 0; i < tweet.getLikes().size(); i++) {
				if (username.equals(tweet.getLikes().get(i).getUserName())) {
					tweetlike = tweet.getLikes().get(i);
				}
			}
			if (tweetlike != null) {
				tweet.getLikes().remove(tweetlike);
				System.out.println(tweet.getLikes());
				likeRepository.deleteById(tweetlike.getId());
				log.info("like is removing from tweet with id => {}", tweet.getId());
				likeMessage = likeMessage + "like is removed";
			} else {
				likeRepository.save(tweetLike);
				tweet.getLikes().add(tweetLike);
				log.info("Tweet Like => {}", tweetLike);
				log.info("like is added to tweet with id => {}", tweet.getId());
				likeMessage = likeMessage + "like is added";
			}
		} else {
			likeRepository.save(tweetLike);
			tweet.getLikes().add(tweetLike);
			log.info("Tweet Like => {}", tweetLike);
			log.info("like is added to tweet with id => {}", tweet.getId());
			likeMessage = likeMessage + "like is added";
		}
		Tweet likeTweet = tweetRepository.save(tweet);
		log.info("Tweet after adding like => {}", likeTweet);
		return likeMessage;
	}

}
