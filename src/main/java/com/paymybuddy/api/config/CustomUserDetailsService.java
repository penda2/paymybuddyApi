package com.paymybuddy.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		return User.builder().username(user.getUsername()).password(user.getPassword()).roles("USER").build();
	}
}
