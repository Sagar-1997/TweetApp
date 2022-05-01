package com.tweetapp.TweetApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.TweetApp.domain.TweetReply;

@Repository
public interface ReplyRepository extends MongoRepository<TweetReply, String> {

}
