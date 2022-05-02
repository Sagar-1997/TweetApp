package com.tweetapp.TweetApp.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
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
@Document(collection = "Tweet_Reply")
public class TweetReply {
	@Id
	@Setter(value = AccessLevel.NONE)
	private String id;
	private String replyText;
	private String tag;
	private String username;
	private LocalDateTime postTime;
}
