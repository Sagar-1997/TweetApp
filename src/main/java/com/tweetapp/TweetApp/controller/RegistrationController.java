package com.tweetapp.TweetApp.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetApp.dto.RegistrationRequest;
import com.tweetapp.TweetApp.mapper.AuthenticationMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RegistrationController {

	private AuthenticationMapper authMapper;

	public RegistrationController(AuthenticationMapper authmapper) {
		this.authMapper = authmapper;
	}

	/* registrated a new user */
	@PostMapping("/register")
	public ResponseEntity<String> registration(@RequestBody @Valid RegistrationRequest registrationRequest,
			BindingResult bindingResult) {
		log.info("inside registration method of RegistrationController");
		String registerUser = authMapper.registerUser(registrationRequest, bindingResult);
		return ResponseEntity.ok().body(registerUser);
	}
}
