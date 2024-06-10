package com.paymybuddy.api.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserService;

@Controller
public class UserRegistrationController {
	Logger logger = LogManager.getLogger(UserRegistrationController.class);

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
	public String registerUser(@ModelAttribute("user") UserModel userModel, Model model) {

		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		userService.save(userModel);
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserModel());
		return "login"; // This should point to login.html
	}
	
	@PostMapping("/login")
	public String logUser(@ModelAttribute("user") UserModel userModel, Model model) {

		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		userService.save(userModel);
		return "redirect:/profile";
	}
	
	/*
	 @GetMapping("/profile")
	    public String profile(Model model, Authentication authentication) {
	        String email = authentication.getName();
	        UserModel user = userService.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
	        model.addAttribute("user", user);
	        return "profile";
	    }

	    @PostMapping("/profile")
	    public String updateProfile(@ModelAttribute("user") UserModel userModel, Authentication authentication) {
	        String email = authentication.getName();
	        UserModel currentUser = userService.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
	        
	        currentUser.setUsername(userModel.getUsername());
	        currentUser.setEmail(userModel.getEmail());

	        // Si le mot de passe est changé, on le réencode
	        if (!userModel.getPassword().isEmpty()) {
	            currentUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
	        }
	        
	        userService.save(currentUser);
	        return "redirect:/profile";
	    }
/**/

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String email = authentication.getName();
        UserModel user = userService.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("activePage", "profile");
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") UserModel userModel, Authentication authentication) {
        String email = authentication.getName();
        UserModel currentUser = userService.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        currentUser.setUsername(userModel.getUsername());
        currentUser.setEmail(userModel.getEmail());

        // Si le mot de passe est changé, on le réencode
        if (!userModel.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
        }
        
        userService.save(currentUser);
        return "redirect:/profile";
    }
	
	@GetMapping("/transaction")
	public String transaction() {
		return "transaction";
	}

	@GetMapping("/connection")
	public String connection() {
		return "connection";
	}

}
