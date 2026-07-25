package com.FullStack.Personal_Digital_Vault.security;

import com.FullStack.Personal_Digital_Vault.entity.User;
import com.FullStack.Personal_Digital_Vault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{

    private final UserRepository userRepository;

    // LOAD USER BY EMAIL
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {

        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email
                        )
                );

        // Return Spring Security user
        return new org.springframework.security.core.userdetails.User(

                user.getEmail(),

                user.getPassword(),

                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()
                        )
                )
        );
    }
}
