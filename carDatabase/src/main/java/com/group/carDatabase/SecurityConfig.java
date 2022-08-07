package com.group.carDatabase;

import com.group.carDatabase.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	// enable users from the database
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// define a password hashing algorithm in the configureGlobal method
		// the password must be hashed using BCrypt before it's saved to the database
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// add our filter class to the Spring Security configuration
	@Autowired
	private AuthenticationFilter authenticationFilter;

	// for the exception handling
	@Autowired
	private AuthEntryPoint exceptionHandler;

	// Spring Security's configure method defines which paths are secured and which are not secured
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		// add this row to give everyone accesses to all endpoints
		//httpSecurity.csrf().disable().cors().and().authorizeRequests().anyRequest().permitAll();

		// define that the POST method request to the /login endpoint is allowed
		// without authentication and that requests to all other endpoints require authentication
		httpSecurity
				// define that Spring Security will never create a session, and therefore we can also disable csrf
				.csrf().disable()
				// cross-origin resource sharing (CORS) filter
				.cors()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				// POST request to /login endpoint is not secured
				.antMatchers(HttpMethod.POST, "/login")
				.permitAll()
				// All other requests are secured
				.anyRequest()
				.authenticated()
				.and()
				// exception handling
				.exceptionHandling()
				.authenticationEntryPoint(exceptionHandler)
				.and()
				// authenticate all other incoming requests
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	// in-memory users
	//@Bean
	//@Override
	//public UserDetailsService userDetailsService() {
	//
	//	UserDetails userDetails = User.withDefaultPasswordEncoder()
	//			.username("user")
	//			.password("password")
	//			.roles("USER")
	//			.build();
	//
	//	return new InMemoryUserDetailsManager(userDetails);
	//}

	// because we have also injected AuthenticationManager into the LoginController
	// class, therefore we have to add the following code to the SecurityConfig class
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {

		return authenticationManager();
	}

	// add a cross-origin resource sharing (CORS) filter to our security configuration class.
	// This is needed for the frontend, which is sending requests from the other origin.
	// The CORS filter intercepts requests, and if these are identified as cross-origin, it adds proper headers to the request
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration config = new CorsConfiguration();

		// allow all origins' HTTP methods and headers
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(false);
		config.applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", config);

		// to explicitly define the origins
		// localhost:3000 is allowed
		//config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

		return source;
	}
}
