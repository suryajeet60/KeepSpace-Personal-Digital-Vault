package com.FullStack.Personal_Digital_Vault.config;

import com.FullStack.Personal_Digital_Vault.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{

    private final CustomUserDetailsService customUserDetailsService;

    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // AUTHENTICATION MANAGER
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    // SECURITY FILTER CHAIN
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {

        http

                // Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                // Enable CORS
                .cors(cors -> {})

                // Session based authentication
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED
                        )
                )

                // API authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        // All other APIs secured
                        .anyRequest().authenticated()
                )

                // Form login disabled
                .formLogin(form -> form.disable())

                // HTTP basic disabled
                .httpBasic(httpBasic -> httpBasic.disable())

                // UserDetailsService
                .userDetailsService(customUserDetailsService);

        return http.build();
    }
}
