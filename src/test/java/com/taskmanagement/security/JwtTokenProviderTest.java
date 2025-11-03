package com.taskmanagement.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider Tests")
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private String jwtSecret;
    private long jwtExpirationMs;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        jwtSecret = "MySecretKeyForJWTTokenGenerationMustBeLongEnoughForHS256Algorithm";
        jwtExpirationMs = 86400000L; // 24 hours

        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", jwtExpirationMs);
    }

    @Test
    @DisplayName("Should generate valid JWT token from authentication")
    void shouldGenerateValidToken() {
        // Arrange
        UserDetails userDetails = User.builder()
            .username("testuser")
            .password("password")
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
            .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Act
        String token = jwtTokenProvider.generateToken(authentication);

        // Assert
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
        assertThat(token.split("\\.")).hasSize(3); // JWT has 3 parts: header.payload.signature
    }

    @Test
    @DisplayName("Should extract username from valid token")
    void shouldExtractUsernameFromToken() {
        // Arrange
        UserDetails userDetails = User.builder()
            .username("testuser")
            .password("password")
            .authorities(Collections.emptyList())
            .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        String username = jwtTokenProvider.getUsernameFromToken(token);

        // Assert
        assertThat(username).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Should validate valid token successfully")
    void shouldValidateValidToken() {
        // Arrange
        UserDetails userDetails = User.builder()
            .username("testuser")
            .password("password")
            .authorities(Collections.emptyList())
            .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should reject invalid token")
    void shouldRejectInvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject token with wrong signature")
    void shouldRejectTokenWithWrongSignature() {
        // Arrange
        String differentSecret = "DifferentSecretKeyThatWillCauseSignatureVerificationToFail1234";
        SecretKey wrongKey = Keys.hmacShaKeyFor(differentSecret.getBytes(StandardCharsets.UTF_8));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        String tokenWithWrongSignature = Jwts.builder()
            .subject("testuser")
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(wrongKey)
            .compact();

        // Act
        boolean isValid = jwtTokenProvider.validateToken(tokenWithWrongSignature);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject expired token")
    void shouldRejectExpiredToken() {
        // Arrange
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        Date pastDate = new Date(now.getTime() - 10000); // Expired 10 seconds ago

        String expiredToken = Jwts.builder()
            .subject("testuser")
            .issuedAt(new Date(now.getTime() - 20000))
            .expiration(pastDate)
            .signWith(key)
            .compact();

        // Act
        boolean isValid = jwtTokenProvider.validateToken(expiredToken);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject null token")
    void shouldRejectNullToken() {
        // Act
        boolean isValid = jwtTokenProvider.validateToken(null);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should reject empty token")
    void shouldRejectEmptyToken() {
        // Act
        boolean isValid = jwtTokenProvider.validateToken("");

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should generate different tokens for different users")
    void shouldGenerateDifferentTokensForDifferentUsers() {
        // Arrange
        UserDetails user1 = User.builder()
            .username("user1")
            .password("password")
            .authorities(Collections.emptyList())
            .build();
        UserDetails user2 = User.builder()
            .username("user2")
            .password("password")
            .authorities(Collections.emptyList())
            .build();

        Authentication auth1 = new UsernamePasswordAuthenticationToken(user1, null);
        Authentication auth2 = new UsernamePasswordAuthenticationToken(user2, null);

        // Act
        String token1 = jwtTokenProvider.generateToken(auth1);
        String token2 = jwtTokenProvider.generateToken(auth2);

        // Assert
        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    @DisplayName("Should extract correct username from token with different user")
    void shouldExtractCorrectUsernameFromDifferentUsers() {
        // Arrange
        UserDetails userDetails = User.builder()
            .username("anotheruser")
            .password("password")
            .authorities(Collections.emptyList())
            .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        String username = jwtTokenProvider.getUsernameFromToken(token);

        // Assert
        assertThat(username).isEqualTo("anotheruser");
    }
}
