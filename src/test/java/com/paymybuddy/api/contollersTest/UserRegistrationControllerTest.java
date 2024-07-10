package com.paymybuddy.api.contollersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import com.paymybuddy.api.controllers.UserRegistrationController;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.services.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl userService;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserRegistrationController userRegistrationController;

	@Test
	@WithMockUser(username = "testUser")
	public void testShowRegistrationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/register")).andExpect(status().isOk())
				.andExpect(view().name("register")).andExpect(model().attributeExists("user"));
	}

	@Test
	@WithMockUser(username = "testUser")
	public void testRegisterUser() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/register").param("email", "alice@exemple.com")
				.param("password", "password").param("confirmPassword", "password").param("username", "Alice")
				.with(SecurityMockMvcRequestPostProcessors.csrf()); // Include CSRF

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(view().name("register"));

		verify(passwordEncoder, never()).encode(anyString());
		verify(userService, never()).save(any(UserModel.class));
	}

	@Test
	@WithMockUser(username = "testUser")
	public void testShowLoginForm() throws Exception {
		UserModel mockUser = new UserModel();
		mockUser.setEmail("testUser");

		mockMvc.perform(get("/login")).andExpect(status().isOk());
	}

}