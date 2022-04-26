package com.tweetapp.TweetApp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.TweetApp.domain.Tweet;
import com.tweetapp.TweetApp.domain.User;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String>{

	List<Tweet> findAllByUser(User user);
}
