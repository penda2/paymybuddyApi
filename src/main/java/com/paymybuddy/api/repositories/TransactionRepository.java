package com.paymybuddy.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.api.models.Transaction;
import com.paymybuddy.api.models.UserModel;

//Repository for managing interaction with the database.

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	// Query method declaration in TransactionRepository
	List<Transaction> findBySenderOrReceiver(UserModel sender, UserModel receiver);
}
