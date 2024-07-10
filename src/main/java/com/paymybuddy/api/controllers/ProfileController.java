package com.paymybuddy.api.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.api.exception.CustomException;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController { //Handles requests for modification of user profile

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public ProfileController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	// Access to the profile page after authentication
	@GetMapping
	public String profile(Model model, Authentication authentication) {
		String email = authentication.getName();
		UserModel user = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("activePage", "profile");
		return "profile";
	}

	// Update user's info and re-encode new password
	@PostMapping
	public String updateProfile(@ Valid @ModelAttribute("user") UserModel userModel, BindingResult result, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "profile";
		}
		String email = authentication.getName();
		UserModel currentUser = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));

		currentUser.setUsername(userModel.getUsername());
		currentUser.setEmail(userModel.getEmail());

		// If the password is updated, it is re-encoded
		if (!userModel.getPassword().isEmpty()) {
			currentUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
		}
		userService.save(currentUser);
		redirectAttributes.addFlashAttribute("successMessage", "Modifications effectuées avec succès!");
		return "redirect:/profile";
	}

	// Update bank account details
	@PostMapping("/updateBankDetails")
	public String updateBankDetails(@RequestParam("bankAccountNumber") String bankAccountNumber,
			@RequestParam("bankRoutingNumber") String bankRoutingNumber, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		String email = authentication.getName();
		UserModel currentUser = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur " + email + " introuvable."));

		try {
			currentUser.setBankAccountNumber(bankAccountNumber);
			currentUser.setBankRoutingNumber(bankRoutingNumber);
			userService.save(currentUser);
			redirectAttributes.addFlashAttribute("successMessage", "Modifications effectuées avec succès!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Echec des modifications.");
		}
		return "redirect:/profile";
	}
}
