package com.FullStack.Personal_Digital_Vault.controller;

import com.FullStack.Personal_Digital_Vault.dto.AuthResponse;
import com.FullStack.Personal_Digital_Vault.dto.LoginRequest;
import com.FullStack.Personal_Digital_Vault.dto.RegisterRequest;
import com.FullStack.Personal_Digital_Vault.entity.User;
import com.FullStack.Personal_Digital_Vault.repository.UserRepository;
import com.FullStack.Personal_Digital_Vault.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{

    private final AuthService authService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    // REGISTER USER
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        AuthResponse response = authService.register(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // LOGIN USER
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {

        // Authenticate user
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        // Store authentication in security context
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        // Create session
        HttpSession session = httpRequest.getSession(true);

        // Save security context in session
        session.setAttribute(
                "SPRING_SECURITY_CONTEXT",
                SecurityContextHolder.getContext()
        );

        // Login response
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    // CURRENT USER
    @GetMapping("/me")
    public ResponseEntity<AuthResponse> currentUser(Authentication authentication)
    {

        if (authentication == null || !authentication.isAuthenticated())
        {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        AuthResponse response = AuthResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .message("Current user fetched successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    // LOGOUT USER
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request)
    {

        HttpSession session = request.getSession(false);

        if (session != null)
        {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(
                "Logout successful"
        );
    }
}
