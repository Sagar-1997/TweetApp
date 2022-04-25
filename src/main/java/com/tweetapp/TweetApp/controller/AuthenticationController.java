package com.tweetapp.TweetApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetApp.dto.PasswordResetRequest;
import com.tweetapp.TweetApp.dto.auth.AuthRequest;
import com.tweetapp.TweetApp.dto.auth.AuthResponse;
import com.tweetapp.TweetApp.mapper.AuthenticationMapper;

@RestController
@RequestMapping("/api/v1.0/tweets/")
public class AuthenticationController {
	
	private AuthenticationMapper authMapper;
	public AuthenticationController(AuthenticationMapper mapper) {
		this.authMapper=mapper;
	}
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest)
    {  
    	System.out.println("AuthRequest "+authRequest);
    	return ResponseEntity.ok().body(authMapper.login(authRequest));
    }
    @PostMapping("/{username}/forgot")
    public ResponseEntity<String> forgotPassword(@PathVariable("username") String username,@RequestBody PasswordResetRequest passwordResetRequest)
    {
    	return ResponseEntity.ok().body(authMapper.forgotPassword(username,passwordResetRequest));
    }
}
