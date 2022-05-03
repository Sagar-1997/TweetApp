package com.tweetapp.TweetApp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.dto.PasswordResetRequest;
import com.tweetapp.TweetApp.dto.RegistrationRequest;
import com.tweetapp.TweetApp.dto.auth.AuthRequest;
import com.tweetapp.TweetApp.dto.auth.AuthResponse;
import com.tweetapp.TweetApp.exception.InputFeildException;
import com.tweetapp.TweetApp.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;


/******This class use to Convert DTO object to Domain Object******/
@Slf4j
@Component
public class AuthenticationMapper {
	@Autowired
	private AuthenticationService authenticationService;

	private ModelMapper modelMapper;

	public AuthenticationMapper(ModelMapper mapper) {
		this.modelMapper = mapper;
	}

	public String registerUser(RegistrationRequest registrationRequest, BindingResult bindingResult) {
		log.info("inside registerUser method of AuthenticationMapper");
		if (bindingResult.hasErrors()) {
			log.info("Inside Feild Exception");
			throw new InputFeildException(bindingResult);
		}
		User userEntity = modelMapper.map(registrationRequest, User.class);
		log.info("registrationRequest is converted to User =>{}", userEntity);
		String registerUser = authenticationService.registerUser(userEntity);
		return registerUser;
	}

	public AuthResponse login(AuthRequest request) {
		log.info("inside login mwthod of AuthenticationMapper");
		AuthResponse response = authenticationService.login(request.getUsername(), request.getPassword());
		return response;
	}

	public String forgotPassword(String username, PasswordResetRequest passwordResetRequest) {
		log.info("inside forgotPassword method of AuthenticationMapper");
		return authenticationService.forgotPassword(username, passwordResetRequest.getPassword(),
				passwordResetRequest.getConfirmPassword());
	}
}
