package com.tweetapp.TweetApp.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTProvider {

	private final String secretKey = Base64.getEncoder().encodeToString("secret".getBytes());

	public String extractUsername(String token) throws ExpiredJwtException {

		String userId = extractClaim(token, Claims::getSubject);
		return userId;
	}

	public Date extractExpiration(String token) throws ExpiredJwtException {
		Date expirationTime = extractClaim(token, Claims::getExpiration);
		return expirationTime;
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) throws ExpiredJwtException {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claims;
	}

	private Boolean isTokenExpired(String token) throws ExpiredJwtException {
		boolean isTokenExpired = extractExpiration(token).before(new Date());
		return isTokenExpired;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String createToken = createToken(claims, userDetails.getUsername());
		return createToken;
	}

	private String createToken(Map<String, Object> claims, String subject) {
		String token = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60 ))// token for 60 mins
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
		return token;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userId = extractUsername(token);
		return (userId.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public Boolean validateToken(String token) throws ExpiredJwtException {
		return !isTokenExpired(token);
	}

}
