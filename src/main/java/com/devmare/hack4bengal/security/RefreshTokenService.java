package com.devmare.hack4bengal.security;

import com.devmare.hack4bengal.data.exceptions.UserInfoException;
import com.devmare.hack4bengal.data.model.RefreshToken;
import com.devmare.hack4bengal.data.model.User;
import com.devmare.hack4bengal.data.repository.RefresTokenRepository;
import com.devmare.hack4bengal.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefresTokenRepository refresTokenRepository;

    @Value("${application.refresh.expiration}")
    public Long REFRSH_TOKEN_EXPIRATION;

    public RefreshToken createRefreshToken(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UserInfoException("User not found");
        }
        User user = optionalUser.get();
        RefreshToken refreshToken = user.getRefreshToken();
        if (refreshToken == null) {
            refreshToken = RefreshToken
                    .builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(REFRSH_TOKEN_EXPIRATION))
                    .user(user)
                    .build();
            refresTokenRepository.save(refreshToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refresTokenRepository.findByRefreshToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpirationTime().compareTo(Instant.now()) < 0) {
            refresTokenRepository.delete(token);
            throw new RuntimeException(token.getRefreshToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}
