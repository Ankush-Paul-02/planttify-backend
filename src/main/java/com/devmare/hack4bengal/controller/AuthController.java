package com.devmare.hack4bengal.controller;

import com.devmare.hack4bengal.business.domain.DefaultResponse;
import com.devmare.hack4bengal.business.dto.AuthResponseDto;
import com.devmare.hack4bengal.business.dto.LoginRequestDto;
import com.devmare.hack4bengal.business.dto.RefreshTokenRequestDto;
import com.devmare.hack4bengal.business.dto.RegisterRequestDto;
import com.devmare.hack4bengal.data.model.RefreshToken;
import com.devmare.hack4bengal.data.repository.RefresTokenRepository;
import com.devmare.hack4bengal.security.AuthService;
import com.devmare.hack4bengal.security.JwtService;
import com.devmare.hack4bengal.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {
        "https://hack4bengal-427818.df.r.appspot.com",
        "http://localhost:8001",
        "http://localhost:8002",
        "http://localhost:8003"
})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final RefresTokenRepository refresTokenRepository;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<DefaultResponse> register(
            @RequestBody RegisterRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                new DefaultResponse(
                        DefaultResponse.Status.SUCCESS,
                        Map.of("data", authService.register(requestDto)),
                        "Registration successful"
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse> login(
            @RequestBody LoginRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                new DefaultResponse(
                        DefaultResponse.Status.SUCCESS,
                        Map.of("data", authService.login(requestDto)),
                        "Login successful"
                )
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<DefaultResponse> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    AuthResponseDto jwtResponse = AuthResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getRefreshToken())
                            .build();
                    return ResponseEntity.ok(
                            new DefaultResponse(
                                    DefaultResponse.Status.SUCCESS,
                                    Map.of("data", jwtResponse),
                                    "Token refreshed successfully"
                            )
                    );
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }

}
