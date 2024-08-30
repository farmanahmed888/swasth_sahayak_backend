package com.example.demo.JWTService;

import com.example.demo.Entity.JWT_entity.RefreshToken;
import com.example.demo.Repository.JWT_Repository.RefreshTokenrep;
import com.example.demo.Repository.JWT_Repository.loginDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshService {

    @Autowired
    loginDetailsRepository loginrep;
    @Autowired
    RefreshTokenrep refreshrep;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .logins(loginrep.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(2592000))
                .build();
        return refreshrep.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshrep.findRefreshTokenByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshrep.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}

