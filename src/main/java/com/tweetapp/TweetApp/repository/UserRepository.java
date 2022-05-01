package com.tweetapp.TweetApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.TweetApp.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);

	Optional<User> findByLoginId(String loginId);

	List<User> findAllByLoginIdContaining(String username);

}
