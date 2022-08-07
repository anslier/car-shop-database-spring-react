package com.group.carDatabase.web;

import com.group.carDatabase.domain.AccountCredentials;
import com.group.carDatabase.service.JwtService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// Login is done by calling the /login endpoint using the POST method and sending the username and password inside the request body
@RestController
public class LoginController {

	// inject a JwtService instance into the controller class because that is used to generate a signed JWT in the case of a successful login
	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	// getToken method that handles the login functionality
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {

		// Generate token and send it in the response Authorization header

		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(
				credentials.getUsername(),
				credentials.getPassword()
		);

		Authentication auth = authenticationManager.authenticate(creds);

		// Generate token
		String jwts = jwtService.getToken(auth.getName());

		// Build response with the generated token
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
						"Authorization")
				.build();
	}
}
