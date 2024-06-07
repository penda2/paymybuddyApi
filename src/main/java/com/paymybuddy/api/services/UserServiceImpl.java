package com.paymybuddy.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Optional<UserModel> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public UserModel save(UserModel userModel) {
		return userRepository.save(userModel);
	}

}
