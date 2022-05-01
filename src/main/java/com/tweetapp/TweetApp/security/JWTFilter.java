package com.tweetapp.TweetApp.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tweetapp.TweetApp.exception.TokenException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private JWTProvider jwtProvider;
	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!request.getServletPath().equals("/login")
				&& !request.getServletPath().equals("/register") && !request.getServletPath().equals("/{username}/forgot")) {
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				log.info("Authorization header => {}",authHeader);
				try {
					String token = authHeader.substring(7);
					log.info("Jwt Token => {}",token);
					String username = jwtProvider.extractUsername(token);
					log.info("Username => {} ",username);
					UserDetails details = userDetailsService.loadUserByUsername(username);
					if (jwtProvider.validateToken(token, details)) {
						UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
								details, null, new ArrayList<>());
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
						log.info("Token is validated!!");
					}
				} catch (Exception ex) {
					throw new TokenException("Token is invalid or expired");
				}

			}
		}
		filterChain.doFilter(request, response);

	}

}
