package com.tweetapp.TweetApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.RegistrationRequest;
import com.tweetapp.TweetApp.mapper.AuthenticationMapper;
import com.tweetapp.TweetApp.repository.UserRepository;

@RestController
@RequestMapping("/api/v1.0/tweets/")
public class RegistrationController {
    @Autowired
    private UserRepository repo;
	private AuthenticationMapper authMapper;
    public RegistrationController(AuthenticationMapper authmapper) {
		this.authMapper=authmapper;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registration(@RequestBody @Valid RegistrationRequest registrationRequest,BindingResult bindingResult) {
		String registerUser = authMapper.registerUser(registrationRequest, bindingResult);
		return ResponseEntity.ok().body(registerUser);
	}
}
