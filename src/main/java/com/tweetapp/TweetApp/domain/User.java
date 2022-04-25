package com.tweetapp.TweetApp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {
	@Id
	@Setter(value = AccessLevel.NONE)
	private String id;
	private String firstName;
	private String lastName;
	@Indexed(unique = true)
	private String email;
	@Indexed(unique = true)
	private String loginId;
	private String password;
	private String confirmPassword;
	private String contactNumber;
}
