package com.tweetapp.TweetApp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.TweetApp.domain.Tweet;
import com.tweetapp.TweetApp.domain.TweetLike;
import com.tweetapp.TweetApp.domain.TweetReply;
import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.tweet.TweetRequest;
import com.tweetapp.TweetApp.repository.LikeRepository;
import com.tweetapp.TweetApp.repository.ReplyRepository;
import com.tweetapp.TweetApp.repository.TweetRepository;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.service.TweetService;

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

	@Override
	public String updateTweet(String id, TweetRequest tweetRequest) {
		Tweet tweet = tweetRepository.findById(id).get();
		System.out.println(tweet);
		tweet.setTweetText(tweetRequest.getTweetText());
		tweet.setPostTime(new Date());
		if (tweetRequest.getTag() != null) {
			tweet.setTag(tweetRequest.getTag());
		}
		tweetRepository.save(tweet);
		return "post for updates !!";
	}

	@Override
	public String deleteTweet(String id) {
		Tweet tweet = tweetRepository.findById(id).get();
		tweetRepository.delete(tweet);
		return "post is deleted";
	}

	@Override
	public String addReply(String username, String id, TweetRequest tweetRequest) {
		Tweet tweet = tweetRepository.findById(id).get();
		TweetReply tweetReply = new TweetReply();
		tweetReply.setPostTime(new Date());
		tweetReply.setReplyText(tweetRequest.getTweetText());
		tweetReply.setTag(tweetRequest.getTag());
		tweetReply.setUsername(username);
		replyRepsitory.insert(tweetReply);
		tweet.getReplies().add(tweetReply);
		tweetRepository.save(tweet);
		return "reply is added";
	}

	@Override
	public String addLike(String username, String id) {
		Tweet tweet = tweetRepository.findById(id).get();
		TweetLike tweetLike= new TweetLike();
    	tweetLike.setLikeDate(new Date());
    	tweetLike.setTweetId(tweet.getId());
    	tweetLike.setUserName(username);
	    if(tweet.getLikes().size()>0)
	    {
	    	System.out.println(tweet.getLikes());
	    	TweetLike tweetlike=null;
	    	for(int i=0;i<tweet.getLikes().size();i++)
	    	{
	    		if(username.equals(tweet.getLikes().get(i).getUserName()))
	    		{
	    			tweetlike=tweet.getLikes().get(i);
	    		}
	    	}
	    	if(tweetlike!=null)
	    	{
	    		System.out.println(tweetlike);
		    	tweet.getLikes().remove(tweetlike);
		    	System.out.println(tweet.getLikes());
		    	likeRepository.deleteById(tweetlike.getId());
	    	}
	    	else
	    	{
	    		likeRepository.save(tweetLike);
		    	tweet.getLikes().add(tweetLike);
	    	}
	    }
	    else
	    {
	    	likeRepository.save(tweetLike);
	    	tweet.getLikes().add(tweetLike);
	    }
		tweetRepository.save(tweet);
		return "like is added";
	}

}
