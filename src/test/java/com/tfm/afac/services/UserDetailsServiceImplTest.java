package com.tfm.afac.services;

import com.tfm.afac.TestConfig;
import com.tfm.afac.data.daos.UserRepository;
import com.tfm.afac.data.model.Role;
import com.tfm.afac.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestConfig
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {

        String email = "test@test.com";
        User user = new User(1,email,"user", "password", Role.CUSTOMER, LocalDateTime.now(),true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {

        String email = "nonexistent@test.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_UserInactive_ThrowsException() {

        String email = "inactive@test.com";
        User inactiveUser = new User(1,email,"user", "password", Role.CUSTOMER, LocalDateTime.now(),true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(inactiveUser));
        assertNotNull(userDetailsService.loadUserByUsername(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

}
