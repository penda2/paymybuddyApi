package com.paymybuddy.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.api.exception.CustomException;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserService;

@Controller
@RequestMapping("/relation")
public class RelationController { //Handles user requests to add an existing relationship to the database

	@Autowired
	private UserService userService;

	// Access to the relation page after authentication
	@GetMapping
	public String relation(Model model, Authentication authentication) {
		String email = authentication.getName();
		UserModel user = userService.findByEmail(email)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("activePage", "relation");
		return "relation";
	}

	// Add new relation with error messages handling
	@PostMapping
	public String addRelation(@RequestParam("email") String email, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		String currentEmail = authentication.getName();
		try {
			UserModel currentUser = userService.findByEmail(currentEmail)
					.orElseThrow(() -> new CustomException("Utilisateur " + currentEmail + " introuvable."));
			UserModel userToAdd = userService.findByEmail(email)
					.orElseThrow(() -> new CustomException("Utilisateur " + email + " introuvable."));
			currentUser.getConnections().add(userToAdd);
			userService.save(currentUser);

			redirectAttributes.addFlashAttribute("successMessage", "Nouvelle relation ajoutée avec succès !");
			return "redirect:/transfert";
		} catch (CustomException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
			return "redirect:/relation";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"Une erreur est survenue. Veuillez réessayer plus tard.");
			return "redirect:/relation";
		}
	}
}
