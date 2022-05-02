package com.tweetapp.TweetApp.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.Getter;

@Getter
public class InputFeildException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Map<String, String> errorMap;

	public InputFeildException(BindingResult bindingResult) {
		this.errorMap = bindingResult.getFieldErrors().stream().collect(
				Collectors.toMap(fieldError -> fieldError.getField() + "Error", FieldError::getDefaultMessage));
	}

}
