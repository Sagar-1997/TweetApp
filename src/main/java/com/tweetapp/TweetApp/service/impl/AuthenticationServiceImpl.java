package com.tweetapp.TweetApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tweetapp.TweetApp.domain.User;
import com.tweetapp.TweetApp.exception.EmailException;
import com.tweetapp.TweetApp.exception.LoginFailedException;
import com.tweetapp.TweetApp.exception.PasswordException;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.exception.UsernameException;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.security.JWTProvider;
import com.tweetapp.TweetApp.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
	private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTProvider jwtProvider;

	@Override
	public String registerUser(User userEntity) {
		System.out.println(userEntity);
		boolean userByEmail = userRepository.findByEmail(userEntity.getEmail()).isPresent();
		boolean userByLoginId = userRepository.findByLoginId(userEntity.getLoginId()).isPresent();
		if(!userEntity.getPassword().equals(userEntity.getConfirmPassword()))
		{
			throw new PasswordException("Password do not match");
		}
		if(userByEmail)
		{
			throw new EmailException("Email already present");
		}
		if(userByLoginId)
		{
			throw new UsernameException("Username already present");
		}
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		userEntity.setConfirmPassword(passwordEncoder.encode(userEntity.getConfirmPassword()));
		userRepository.save(userEntity);
		return "User is successfully registered";
	}

	@Override
	public String login(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(username,password);
		Authentication authenticate=null;
		try {
			authenticate = authManager.authenticate(authenticationRequest);
		}
		catch(BadCredentialsException ex)
		{
			throw new LoginFailedException("Username or password is wrong");
		}
		UserDetails user=(org.springframework.security.core.userdetails.User)authenticate.getPrincipal();
		return jwtProvider.generateToken(user);
	}

	@Override
	public String forgotPassword(String username,String password, String confirmPassword) {
		User user = userRepository.findByLoginId(username).orElseThrow(()->new UserNotFoundException("User with "+username+" is not found"));
		if(!password.equals(confirmPassword))
		{
			throw new PasswordException("Password do not match");
		}
		user.setPassword(passwordEncoder.encode(password));
		user.setConfirmPassword(passwordEncoder.encode(confirmPassword));
		userRepository.save(user);
		return "Password for "+username+" is changed !!";
	}
    
}
