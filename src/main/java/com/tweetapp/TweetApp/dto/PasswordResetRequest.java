package com.tweetapp.TweetApp.dto;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String password;
    private String confirmPassword;
}
