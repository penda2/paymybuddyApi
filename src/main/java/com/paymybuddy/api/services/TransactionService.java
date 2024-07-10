package com.paymybuddy.api.services;

import java.util.List;

import com.paymybuddy.api.models.Transaction;
import com.paymybuddy.api.models.UserModel;

public interface TransactionService {

	Transaction saveTransaction(Transaction transaction);

	List<Transaction> findAllTransactionsByUser(UserModel user);

	void processTransaction(Transaction transaction, String email);

	void deposit(UserModel user, double amount);

	void transferToBank(UserModel user, double amount);

	boolean hasBankDetails(UserModel user);
}