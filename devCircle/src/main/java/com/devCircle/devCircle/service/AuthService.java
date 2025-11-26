package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.AuthResponseDTO;
import com.devCircle.devCircle.dto.LoginRequestDTO;
import com.devCircle.devCircle.dto.RegisterRequestDTO;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO registerUser(RegisterRequestDTO request) {
        User user = User.builder()
                .displayName(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("BEGINNER")
                .avatarUrl("https://cdn.pfps.gg/pfps/8705-cat-cartoon.png")
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO loginUser(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));
        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token);
    }
}
