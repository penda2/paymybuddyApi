package com.paymybuddy.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.api.exception.CustomException;
import com.paymybuddy.api.models.Transaction;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService { // Implement business logic, manage transactions

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserService userService;

	private static final double TRANSACTION_DEDUCTION = 0.005; // 0.5% deduction for each transaction

	private static final double MINIMUM_TRANSFER_AMOUNT = 5.0; // Minimum transaction amount allowed

	@Override
	@Transactional
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Transaction> findAllTransactionsByUser(UserModel user) {
		return transactionRepository.findBySenderOrReceiver(user, user);
	}

	// Transaction processing logic
	@Override
	@Transactional
	public void processTransaction(Transaction transaction, String email) {
		Double transferAmount = transaction.getAmount();
		if (transferAmount < MINIMUM_TRANSFER_AMOUNT) {
			throw new CustomException("Montant minimum autorisé : " + MINIMUM_TRANSFER_AMOUNT + "€.");
		}

		UserModel sender = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur: " + email + " introuvable."));

		Double transactionTax = calculateTransactionTax(transferAmount); // tax calculation
		Double totalAmount = transferAmount + transactionTax;

		if (sender.getBalance() < totalAmount) {
			throw new CustomException("Solde insuffisant pour effectuer ce transfert.");
		}

		sender.setBalance(sender.getBalance() - totalAmount); // balance update
		transaction.setSender(sender);
		saveTransaction(transaction);
		userService.save(sender);
	}

	// Deposit processing logic, tax calculation and balance update
	@Override
	@Transactional
	public void deposit(UserModel user, double amount) {
		if (amount < MINIMUM_TRANSFER_AMOUNT) {
			throw new CustomException("Montant minimum autorisé : " + MINIMUM_TRANSFER_AMOUNT + "€.");
		}

		Double transactionTax = calculateTransactionTax(amount);
		Double totalAmount = amount - transactionTax;
		user.setBalance(user.getBalance() + totalAmount);
		userService.save(user);
	}

	// Transfer to user's bank processing logic, tax calculation and balance update
	@Override
	@Transactional
	public void transferToBank(UserModel user, double amount) {
		if (user.getBankAccountNumber() == null || user.getBankRoutingNumber() == null) {
			throw new CustomException("Veuillez ajouter un compte bancaire.");
		}
		if (amount < MINIMUM_TRANSFER_AMOUNT) {
			throw new CustomException("Montant minimum autorisé : " + MINIMUM_TRANSFER_AMOUNT + "€");
		}
		double totalAmount = amount + calculateTransactionTax(amount);
		if (user.getBalance() < totalAmount) {
			throw new CustomException("Solde insuffisant pour effectuer ce transfert.");
		}
		user.setBalance(user.getBalance() - totalAmount);
		userService.save(user);
	}

	// Method of verifying user's bank details
	@Override
	public boolean hasBankDetails(UserModel user) {
		return user.getBankAccountNumber() != null && !user.getBankAccountNumber().isEmpty()
				&& user.getBankRoutingNumber() != null && !user.getBankRoutingNumber().isEmpty();
	}

	// Transaction tax calculation
	private Double calculateTransactionTax(Double amount) {
		return amount * TRANSACTION_DEDUCTION;
	}
}
