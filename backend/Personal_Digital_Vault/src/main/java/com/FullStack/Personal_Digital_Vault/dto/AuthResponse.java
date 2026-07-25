package com.FullStack.Personal_Digital_Vault.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse
{

    private Long id;

    private String fullName;

    private String email;

    private String role;

    private String message;
}
