package com.FullStack.Personal_Digital_Vault.service.impl;

import com.FullStack.Personal_Digital_Vault.dto.AuthResponse;
import com.FullStack.Personal_Digital_Vault.dto.LoginRequest;
import com.FullStack.Personal_Digital_Vault.dto.RegisterRequest;
import com.FullStack.Personal_Digital_Vault.entity.User;
import com.FullStack.Personal_Digital_Vault.repository.UserRepository;
import com.FullStack.Personal_Digital_Vault.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService
{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    // REGISTER USER
    @Override
    public AuthResponse register(RegisterRequest request)
    {

        // Check email already exists
        if (userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email already registered");
        }

        // Create user
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .role("USER")
                .build();

        // Save user
        User savedUser = userRepository.save(user);

        // Return response
        return AuthResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .message("User registered successfully")
                .build();
    }

    // LOGIN USER
    @Override
    public AuthResponse login(LoginRequest request)
    {

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user from database
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Return response
        return AuthResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .message("Login successful")
                .build();
    }
}
