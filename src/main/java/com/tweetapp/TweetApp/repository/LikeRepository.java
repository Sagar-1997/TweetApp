package com.tweetapp.TweetApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.TweetApp.domain.TweetLike;

public interface LikeRepository extends MongoRepository<TweetLike, String>{

}
