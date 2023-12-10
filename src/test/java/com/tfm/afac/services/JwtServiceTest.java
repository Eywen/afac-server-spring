package com.tfm.afac.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tfm.afac.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestConfig
public class JwtServiceTest {

    @MockBean
    private DecodedJWT decodedJWT;

    @Autowired
    private JwtService jwtService;

    private String validToken;

    @BeforeEach
    void setUp() {
        validToken = jwtService.createToken("test@example.com", "Test User", "ROLE_USER");
    }

    @Test
    void extractToken_ValidBearer_ReturnsToken() {

        String bearerToken = "Bearer " + validToken;
        String result = jwtService.extractToken(bearerToken);
        assertEquals(validToken, result);
    }

    @Test
    void extractToken_InvalidBearer_ReturnsEmptyString() {

        String invalidBearerToken = "InvalidBearerToken";
        String result = jwtService.extractToken(invalidBearerToken);
        assertEquals("", result);
    }

    @Test
    void createToken_GeneratesToken() {

        when(decodedJWT.getClaim(any())).thenReturn(null);
        String token = jwtService.createToken("test@example.com", "Test User", "ROLE_USER");
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void user_ValidToken_ReturnsUser() {
        when(decodedJWT.getClaim("user")).thenAnswer(invocation -> {
            Supplier<String> supplier = invocation.getArgument(0);
            return supplier.get();
        });

        String user = jwtService.user(validToken);
        assertEquals("test@example.com", user);
    }

    @Test
    void user_InvalidToken_ReturnsEmptyString() {

        String user = jwtService.user("InvalidToken");
        assertEquals("", user);
    }

}
