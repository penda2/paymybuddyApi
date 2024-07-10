package com.paymybuddy.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // activate web security
public class SecurityConfig { // application security configuration

	@Bean
	public PasswordEncoder passwordEncoder() { // helps to encrypt passwords with the BCrypt algorithm
		return new BCryptPasswordEncoder();
	}

	// Validates the user's credentials
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
			CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
		return authenticationManagerBuilder.build();
	}

	// Securing requests for pages accessibility
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Disabling CSRF protection
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/register", "/login", "/css/**").permitAll() // Permit access to
																						// register and login
							.anyRequest().authenticated(); // All other requests need authentication
				}).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/profile", true) // Redirect to profile
																									// after successful
																									// login
						.failureUrl("/login?error=true") // Redirect on authentication failure
						.usernameParameter("email") // Use email as the username parameter
						.passwordParameter("password") // Ensure this matches the name in login form
						.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout"));
		return http.build();
	}
}