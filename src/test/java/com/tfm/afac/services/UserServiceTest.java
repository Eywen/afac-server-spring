package com.tfm.afac.services;

import com.tfm.afac.TestConfig;
import com.tfm.afac.data.daos.UserRepository;
import com.tfm.afac.data.model.Role;
import com.tfm.afac.data.model.User;
import com.tfm.afac.services.exceptions.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.tfm.afac.services.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@TestConfig
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    JwtService jwtService;

    User userEntity;

    @BeforeEach
    void onInit(){
        userEntity = User.builder().
                id(1)
                .email("user1@prueba.com")
                .password("$2a$10$La/204IX68uuOacbmsLs3.YHvEpvB.UZeVQUU18PQy6Uc.1Knc0xK")
                .active(true)
                .registrationDate( LocalDateTime.now())
                .role(Role.ADMIN)
                .firstName("user1")
                .build();
    }

    @Test
    void loginTest(){
        String token = "$2a$10$La/204IX68uuOacbmsLs3.YHvEpvB.UZeVQUU18PQy6Uc.1Knc0xK";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(jwtService.createToken(anyString(), anyString(), anyString())).thenReturn(token);

        Optional<String> login = userService.login("'user1@prueba.com'");
        assertTrue(login.isPresent());
    }

    @Test
    void createUser_InsufficientRole_ThrowsForbiddenException() {
        // Arrange
        User user = new User();
        user.setEmail("new@example.com");
        user.setRole(Role.CUSTOMER);


        // Act and Assert
        assertThrows(ForbiddenException.class, () -> userService.createUser(user, Role.CUSTOMER));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_UserAlreadyExists_ThrowsForbiddenException() {
        // Arrange
        User user = new User();
        user.setEmail("existing@example.com");
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(ForbiddenException.class, () -> userService.createUser(user, Role.CUSTOMER));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_ValidUser_CreatesUser() {
        User user = new User();
        user.setEmail("new@example.com");
        user.setRole(Role.CUSTOMER);
        user.setFirstName("firstName");
        user.setPassword("password");
        user.setRegistrationDate(LocalDateTime.now());
        user.setActive(true);

        userService.createUser(user, Role.ADMIN);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void readByMobileAssured_UserExists_ReturnsUser() {

        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.readByMobileAssured("test@example.com");

        assertEquals(user, result);
    }

    @Test
    void readByMobileAssured_UserDoesNotExist_ThrowsNotFoundException() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.readByMobileAssured("nonexistent@example.com"));
    }


}
