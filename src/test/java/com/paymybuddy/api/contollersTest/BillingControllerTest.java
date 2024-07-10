package com.paymybuddy.api.contollersTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.paymybuddy.api.controllers.BillingController;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserServiceImpl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BillingController.class)
public class BillingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl userService;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Test
	@WithMockUser(username = "testUser")
	public void showBillingPageTest() throws Exception {
		UserModel mockUser = new UserModel();
		mockUser.setEmail("testUser");

		when(userService.findByEmail("testUser")).thenReturn(Optional.of(mockUser));
		mockMvc.perform(get("/billing")).andExpect(status().isOk()).andExpect(view().name("billing"))
				.andExpect(model().attributeExists("user"));
	}
}
