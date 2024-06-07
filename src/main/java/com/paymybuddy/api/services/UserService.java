package com.paymybuddy.api.services;

import java.util.Optional;

import com.paymybuddy.api.models.UserModel;

public interface UserService {
	
	UserModel save(UserModel userModel);

	Optional<UserModel> findByUsername(String username);

}