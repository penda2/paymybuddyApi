package com.paymybuddy.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.api.models.UserModel;

//Repository for managing interaction with the database.

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
	// Query methods declaration in UserRepository
	Optional<UserModel> findByUsername(String username);

	Optional<UserModel> findByEmail(String email);

	List<UserModel> findAllByIdNot(Integer id);
}
