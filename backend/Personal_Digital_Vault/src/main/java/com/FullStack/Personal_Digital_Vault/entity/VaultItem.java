package com.FullStack.Personal_Digital_Vault.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vault_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaultItem
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title of item
    @Column(nullable = false, length = 200)
    private String title;

    // Main content
    @Column(columnDefinition = "TEXT")
    private String content;

    // Dynamic category
    @Column(length = 100)
    private String category;

    // Tags stored as comma separated values
    @Column(length = 500)
    private String tags;

    // Optional URL
    @Column(length = 1000)
    private String referenceUrl;

    // Optional priority
    private String priority;

    // Favorite marker
    private boolean favorite;

    // Archive marker
    private boolean archived;

    // Created timestamp
    private LocalDateTime createdAt;

    // Updated timestamp
    private LocalDateTime updatedAt;

    // Owner of vault item
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // Auto set create time
    @PrePersist
    public void onCreate()
    {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Auto update time
    @PreUpdate
    public void onUpdate()
    {
        this.updatedAt = LocalDateTime.now();
    }
}
