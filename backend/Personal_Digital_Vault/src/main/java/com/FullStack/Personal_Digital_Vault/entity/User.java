package com.FullStack.Personal_Digital_Vault.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name
    @Column(nullable = false, length = 100)
    private String fullName;

    // Email for login
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // Encrypted password
    @Column(nullable = false)
    private String password;

    // Role
    @Column(nullable = false)
    private String role;

    // Account created time
    private LocalDateTime createdAt;

    // User's vault items
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VaultItem> vaultItems;

    // Auto create timestamp
    @PrePersist
    public void onCreate()
    {
        this.createdAt = LocalDateTime.now();
    }
}
