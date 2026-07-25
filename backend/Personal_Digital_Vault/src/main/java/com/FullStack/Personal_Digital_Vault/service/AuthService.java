package com.FullStack.Personal_Digital_Vault.service;

import com.FullStack.Personal_Digital_Vault.dto.AuthResponse;
import com.FullStack.Personal_Digital_Vault.dto.LoginRequest;
import com.FullStack.Personal_Digital_Vault.dto.RegisterRequest;

public interface AuthService
{
    // Register new user
    AuthResponse register(RegisterRequest request);

    // Login user
    AuthResponse login(LoginRequest request);
}
