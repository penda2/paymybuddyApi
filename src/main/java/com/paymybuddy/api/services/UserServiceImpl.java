package com.paymybuddy.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService { //Implement business logic, manage transactions

	//Dependency injection 
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<UserModel> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public UserModel save(UserModel userModel) {
		if (userModel.getId() == null && userModel.getBalance() == 0.0) {
			userModel.setBalance(300.0);
		}
		return userRepository.save(userModel);
	}

	@Override
    @Transactional(readOnly = true)
	public Optional<UserModel> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserModel getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	public List<UserModel> findAllExceptCurrentUser(Integer id) {
		return userRepository.findAllByIdNot(id);
	}
}