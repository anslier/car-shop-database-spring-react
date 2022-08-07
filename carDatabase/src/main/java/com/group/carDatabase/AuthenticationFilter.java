package com.group.carDatabase;

import com.group.carDatabase.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// using filters that allow us to perform some operations before a request goes to the controller or before a response is sent to a client
// use a filter class to authenticate all other incoming requests
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	// inject a JwtService instance into the filter class because that is needed to verify a token from the request
	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		// Get token from the Authorization header
		String jws = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (jws != null) {

			// Verify token and get user
			String user = jwtService.getAuthUser(request);

			// Authenticate
			Authentication authentication =	new UsernamePasswordAuthenticationToken(user, null, java.util.Collections.emptyList());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}
