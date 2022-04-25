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

@Component
public class AuthenticationMapper {
	@Autowired
	private AuthenticationService authenticationService;
	
	private ModelMapper modelMapper;
	
	public AuthenticationMapper(ModelMapper mapper) {
		this.modelMapper=mapper;
	}
	
	public String registerUser(RegistrationRequest registrationRequest,BindingResult bindingResult)
	{
		System.out.println(registrationRequest);
		if(bindingResult.hasErrors())
		{
			throw new InputFeildException(bindingResult);
		}
		User userEntity = modelMapper.map(registrationRequest, User.class);
		System.out.println(userEntity);
		return authenticationService.registerUser(userEntity);
	}
	public AuthResponse login(AuthRequest request)
	{
		String token = authenticationService.login(request.getUsername(),request.getPassword());
		AuthResponse response = new AuthResponse();
		response.setToken(token);
		response.setUsername(request.getUsername());
		return response;
	}

	public String forgotPassword(String username, PasswordResetRequest passwordResetRequest) {
		return authenticationService.forgotPassword(username,passwordResetRequest.getPassword(),passwordResetRequest.getConfirmPassword());
	}
}
