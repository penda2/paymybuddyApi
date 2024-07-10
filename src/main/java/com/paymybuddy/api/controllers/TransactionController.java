package com.paymybuddy.api.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.api.exception.CustomException;
import com.paymybuddy.api.models.Transaction;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.TransactionService;
import com.paymybuddy.api.services.UserService;

@Controller
@RequestMapping("/transfert")
public class TransactionController { //Handles requests for user money transfer and redirects to given pages

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	// Access to the transfer page after authentication
	@GetMapping
	public String showTransfer(Model model, Authentication authentication) {
		// Get the currently logged in user
		String email = authentication.getName();
		UserModel currentUser = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur " + email + " introuvable"));

		// Get user transactions
		List<Transaction> transactions = transactionService.findAllTransactionsByUser(currentUser);

		// Adding user relationships/connections to the template 
		Set<UserModel> userRelations = currentUser.getConnections();

		model.addAttribute("activePage", "transfert");
		model.addAttribute("transactions", transactions);
		model.addAttribute("userRelations", userRelations);
		model.addAttribute("transaction", new Transaction());
		return "transfert";
	}

	// Logic user's money transfer
	@PostMapping
	public String submitTransfer(@ModelAttribute("transaction") Transaction transaction, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		String email = authentication.getName();
		try {
			transactionService.processTransaction(transaction, email);
			redirectAttributes.addFlashAttribute("successMessage", "Transfert effectué avec succès !");
		} catch (CustomException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/transfert";
	}
}
