package com.tweetapp.TweetApp.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {

	private HttpStatus status;
	private String errorMessage;
	private LocalDateTime datetime;
}
