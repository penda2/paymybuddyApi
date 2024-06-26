package com.paymybuddy.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.api.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
	Optional<UserModel> findByUsername(String username);

	Optional<UserModel> findByEmail(String email);

}
