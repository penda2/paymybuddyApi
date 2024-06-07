package com.paymybuddy.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserModel());
		return "register"; // This should point to register.html
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") UserModel userModel) {
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		userService.save(userModel);
		return "redirect:/login"; // Redirect to avoid form resubmission
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserModel());
		return "login"; // This should point to login.html
	}

	@GetMapping("/transaction")
	public String transaction() {
		return "transaction";
	}

	@GetMapping("/profile")
	public String profile() {
		return "profile";
	}

	@GetMapping("/connection")
	public String connection() {
		return "connection";
	}

}
