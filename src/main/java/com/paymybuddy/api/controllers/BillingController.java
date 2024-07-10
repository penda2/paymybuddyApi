package com.paymybuddy.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.api.exception.CustomException;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserService;

@Controller
public class BillingController { //Handles requests and redirects to given page
	
	@Autowired
	private UserService userService;
	
	// Access the future billing page not yet implemented
	@GetMapping("/billing")
	public String showBillinPage(Model model, Authentication authentication) {
		String email = authentication.getName();
		UserModel user = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("activePage", "billing");
		return "billing";
	}
}
