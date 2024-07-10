package com.paymybuddy.api.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserRegistrationController { //Handles requests for users login and registration and redirects to given pages

	Logger logger = LogManager.getLogger(UserRegistrationController.class);

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public UserRegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	// Access to the register page for user registration
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserModel());
		return "register";
	}

	// Add new user after validating the required fields
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") UserModel userModel, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register";
		}
		if (userModel.getBalance() == 0.0) {
			userModel.setBalance(300.0);
		}
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		userService.save(userModel);
		return "redirect:/login";
	}

	// Access to the login page after authentication
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserModel());
		return "login";
	}
}
