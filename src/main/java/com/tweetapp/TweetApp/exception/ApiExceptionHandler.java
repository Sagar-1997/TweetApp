package com.tweetapp.TweetApp.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tweetapp.TweetApp.dto.ExceptionResponse;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(InputFeildException.class)
	public ResponseEntity<Map<String, String>> handlerInputFieldException(InputFeildException inputFeildException) {
		return ResponseEntity.badRequest().body(inputFeildException.getErrorMap());
	}

	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<ExceptionResponse> handlerPasswordException(PasswordException passwordException) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().datetime(LocalDateTime.now())
				.errorMessage(passwordException.getPasswordError()).status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.badRequest().body(exceptionResponse);
	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<ExceptionResponse> handlerEmailException(EmailException emailException) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().datetime(LocalDateTime.now())
				.errorMessage(emailException.getEmailError()).status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.badRequest().body(exceptionResponse);
	}

	@ExceptionHandler(UsernameException.class)
	public ResponseEntity<ExceptionResponse> handlerUsernameException(UsernameException usernameException) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().datetime(LocalDateTime.now())
				.errorMessage(usernameException.getUsernameError()).status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.badRequest().body(exceptionResponse);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handlerUserNotFoundException(UserNotFoundException userNotFoundException) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().datetime(LocalDateTime.now())
				.errorMessage(userNotFoundException.getUserNotFound()).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<ExceptionResponse> handlerLoginFailedException(LoginFailedException loginFailedException) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().datetime(LocalDateTime.now())
				.errorMessage(loginFailedException.getLoginFailed()).status(HttpStatus.UNAUTHORIZED).build();
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<ExceptionResponse> handlerJwtTokenException(TokenException jwtTokenException) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().datetime(LocalDateTime.now())
				.errorMessage(jwtTokenException.getMessage()).status(HttpStatus.FORBIDDEN).build();
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(TweetNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handlerJwtTokenException(TweetNotFoundException tweetException) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().datetime(LocalDateTime.now())
				.errorMessage(tweetException.getTweetNotFound()).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

}
