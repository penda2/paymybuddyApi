package com.paymybuddy.api.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.repositories.UserRepository;
import com.paymybuddy.api.services.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private UserRepository userRepository;
	
    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;
    
    @Test
    public void testFindByUsername() {
        UserModel user = new UserModel();
        user.setUsername("Alice");
        when(userRepository.findByUsername("Alice")).thenReturn(Optional.of(user));

        Optional<UserModel> result = userServiceImpl.findByUsername("Alice");
        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getUsername());
    }

    @Test
    public void testSaveNewUserWithInitialBalance() {
        UserModel user = new UserModel();
        when(userRepository.save(user)).thenAnswer(i -> {
            UserModel u = i.getArgument(0);
            u.setId(1);
            return u;
        });

        UserModel savedUser = userServiceImpl.save(user);
        assertEquals(300.0, savedUser.getBalance());
        assertNotNull(savedUser.getId());
    }

    @Test
    public void testSaveExistingUserWithoutChangingBalance() {
        UserModel user = new UserModel();
        user.setId(1);
        user.setBalance(500.0);
        when(userRepository.save(user)).thenReturn(user);

        UserModel savedUser = userServiceImpl.save(user);
        assertEquals(500.0, savedUser.getBalance());
    }

    @Test
    public void testFindByEmail() {
        UserModel user = new UserModel();
        user.setEmail("alice@example.com");
        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));

        Optional<UserModel> result = userServiceImpl.findByEmail("alice@example.com");
        assertTrue(result.isPresent());
        assertEquals("alice@example.com", result.get().getEmail());
    }

    @Test
    public void testGetCurrentUser() {
        UserModel user = new UserModel();
        user.setUsername("currentUser");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("currentUser");
        when(userRepository.findByUsername("currentUser")).thenReturn(Optional.of(user));
        SecurityContextHolder.setContext(securityContext);

        UserModel result = userServiceImpl.getCurrentUser();
        assertNotNull(result);
        assertEquals("currentUser", result.getUsername());
    }

    @Test
    public void testFindAllExceptCurrentUser() {
        UserModel user1 = new UserModel();
        user1.setId(1);
        UserModel user2 = new UserModel();
        user2.setId(2);

        when(userRepository.findAllByIdNot(1)).thenReturn(Arrays.asList(user2));

        List<UserModel> result = userServiceImpl.findAllExceptCurrentUser(1);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getId().intValue());
    }
}