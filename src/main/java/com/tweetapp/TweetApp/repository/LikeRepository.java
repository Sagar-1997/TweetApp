package com.tweetapp.TweetApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.TweetApp.domain.TweetLikes;

public interface LikeRepository extends MongoRepository<TweetLikes, String> {

}
