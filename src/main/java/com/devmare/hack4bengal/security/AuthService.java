package com.devmare.hack4bengal.security;

import com.devmare.hack4bengal.business.dto.AuthResponseDto;
import com.devmare.hack4bengal.business.dto.LoginRequestDto;
import com.devmare.hack4bengal.business.dto.RegisterRequestDto;
import com.devmare.hack4bengal.data.enums.Role;
import com.devmare.hack4bengal.data.exceptions.UserInfoException;
import com.devmare.hack4bengal.data.model.RefreshToken;
import com.devmare.hack4bengal.data.model.Roles;
import com.devmare.hack4bengal.data.model.User;
import com.devmare.hack4bengal.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthResponseDto register(RegisterRequestDto requestDto) {
        Optional<User> userOptional = userRepository.findByEmail(requestDto.getEmail());
        if (userOptional.isPresent()) {
            throw new UserInfoException("User already exists with email: " + requestDto.getEmail());
        }

        if (requestDto.getEmail() == null || requestDto.getEmail().isEmpty()) {
            throw new UserInfoException("Email is mandatory");
        }
        if (requestDto.getName() == null || requestDto.getName().isEmpty()) {
            throw new UserInfoException("Name is mandatory");
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new UserInfoException("Password is mandatory");
        }
        if (requestDto.getGender() == null || requestDto.getGender().isEmpty()) {
            throw new UserInfoException("Gender is mandatory");
        }
        if (requestDto.getPhone() == null || requestDto.getPhone().isEmpty()) {
            throw new UserInfoException("Phone is mandatory");
        }

        User newUser = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .gender(requestDto.getGender().toUpperCase())
                .password(requestDto.getPassword())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialNonExpired(true)
                .isEnabled(true)
                .build();


        Set<Roles> defaultRoles = new HashSet<>();
        Roles role = Roles.builder()
                .name(Role.USER)
                .build();
        defaultRoles.add(role);

        newUser.setRoles(defaultRoles);

        User savedUser = userRepository.save(newUser);

        String accessToken = jwtService.generateToken(savedUser);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        Optional<User> optionalUser = userRepository.findByEmail(loginRequestDto.getEmail());
        if (optionalUser.isEmpty()) {
            throw new UserInfoException("User with email " + loginRequestDto.getEmail() + " does not exist");
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new UserInfoException("Invalid password for user with email " + loginRequestDto.getEmail());
        }

        String accessToken = jwtService.generateToken(new HashMap<>(), user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
