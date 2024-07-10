package com.paymybuddy.api.services;

import java.util.List;
import java.util.Optional;

import com.paymybuddy.api.models.UserModel;

public interface UserService {

	UserModel save(UserModel userModel);

	Optional<UserModel> findByUsername(String username);

	Optional<UserModel> findByEmail(String email);

	UserModel getCurrentUser();

	List<UserModel> findAllExceptCurrentUser(Integer id);
}
