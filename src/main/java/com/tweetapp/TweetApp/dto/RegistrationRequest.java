package com.tweetapp.TweetApp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class RegistrationRequest {
	@NotBlank(message = "first name not be blank or null")
	private String firstName;
	@NotBlank(message = "lastName not be blank or null")
	private String lastName;
	@NotBlank(message = "email not be blank or null")
	private String email;
	@NotBlank(message = "loginId not be blank or null")
	private String loginId;
	@NotBlank(message = "password not be blank or null")
	private String password;
	@NotBlank(message = "Confirm password not be blank or null")
	private String confirmPassword;
	@Pattern(regexp = "^[789]\\d{9}$", message = "Phone Number should be equal to 10 digits and starts with 7,8 or 9")
	private String contactNumber;
}
