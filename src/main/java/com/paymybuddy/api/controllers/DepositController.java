package com.paymybuddy.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.api.exception.CustomException;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.TransactionService;
import com.paymybuddy.api.services.UserService;

@Controller
public class DepositController { //Handles requests for user money deposit and transfer and redirects to given pages

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	// Logic user's money deposit 
	@PostMapping("/deposit")
	public String deposit(@RequestParam("amount") double amount, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		String email = authentication.getName();
		UserModel user = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable"));
		try {
			transactionService.deposit(user, amount);
			redirectAttributes.addFlashAttribute("successMessage", "Virement sur ce compte effectué avec succès !");
		} catch (CustomException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/deposit";
	}

	// Access to the deposit page after authentication
	@GetMapping("/deposit")
	public String showDepositForm(Model model, Authentication authentication) {
		String email = authentication.getName();
		UserModel user = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("activePage", "deposit");
		return "deposit";
	}

	// Method of transferring money from application to his bank 
	@PostMapping("/transferToBank")
	public String transferToBank(@RequestParam("amount") double amount, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		String email = authentication.getName();
		UserModel currentUser = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("User not found with email: " + email));
		try {
			transactionService.transferToBank(currentUser, amount);
			redirectAttributes.addFlashAttribute("successMessage", "Transfert vers la banque effectué avec succès !");
		} catch (CustomException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/deposit";
	}
}
