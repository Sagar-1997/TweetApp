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
import com.tweetapp.TweetApp.dto.auth.AuthResponse;
import com.tweetapp.TweetApp.exception.EmailException;
import com.tweetapp.TweetApp.exception.LoginFailedException;
import com.tweetapp.TweetApp.exception.PasswordException;
import com.tweetapp.TweetApp.exception.UserNotFoundException;
import com.tweetapp.TweetApp.exception.UsernameException;
import com.tweetapp.TweetApp.repository.UserRepository;
import com.tweetapp.TweetApp.security.JWTProvider;
import com.tweetapp.TweetApp.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
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
		log.info("Inside registerUser method of AuthenticationServiceImpl");
		boolean userByEmail = userRepository.findByEmail(userEntity.getEmail()).isPresent();
		boolean userByLoginId = userRepository.findByLoginId(userEntity.getLoginId()).isPresent();
		if (!userEntity.getPassword().equals(userEntity.getConfirmPassword())) {
			log.info("Password do not match");
			throw new PasswordException("Password do not match");
		}
		if (userByEmail) {
			log.info("Email is already present");
			throw new EmailException("Email already present");
		}
		if (userByLoginId) {
			log.info("Username is already present");
			throw new UsernameException("Username already present");
		}
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		userEntity.setConfirmPassword(passwordEncoder.encode(userEntity.getConfirmPassword()));
		User registeredUser = userRepository.save(userEntity);
		log.info("User is successfully registerd with details => {}", registeredUser);
		return "User is successfully registered";
	}

	@Override
	public AuthResponse login(String username, String password) {
		log.info("Inside login method of AuthenticationServiceImpl");
		AuthResponse reponse = new AuthResponse();
		UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(username,
				password);
		Authentication authenticate = null;
		try {
			authenticate = authManager.authenticate(authenticationRequest);
		} catch (BadCredentialsException ex) {
			log.info("Password is wrong");
			throw new LoginFailedException("password is wrong");
		}
		UserDetails user = (org.springframework.security.core.userdetails.User) authenticate.getPrincipal();

		String generateToken = jwtProvider.generateToken(user);
		reponse.setUsername(user.getUsername());
		reponse.setExpirationTime(jwtProvider.extractExpiration(generateToken));
		reponse.setToken(generateToken);
		log.info("Login is successfull and token is genrated");
		return reponse;
	}

	@Override
	public String forgotPassword(String username, String password, String confirmPassword) {
		log.info("Inside forgotPassword method of AuthenticationServiceImpl");
		User user = userRepository.findByLoginId(username)
				.orElseThrow(() -> new UserNotFoundException("User with " + username + " is not found"));
		if (!password.equals(confirmPassword)) {
			log.info("Password do not match");
			throw new PasswordException("Password do not match");
		}
		user.setPassword(passwordEncoder.encode(password));
		user.setConfirmPassword(passwordEncoder.encode(confirmPassword));
		userRepository.save(user);
		log.info("password is changed for " + username);
		return "Password for " + username + " is changed !!";
	}

}
