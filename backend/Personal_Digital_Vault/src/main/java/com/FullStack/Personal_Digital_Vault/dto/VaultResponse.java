package com.FullStack.Personal_Digital_Vault.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaultResponse
{

    private Long id;

    private String title;

    private String content;

    private String category;

    private String tags;

    private String referenceUrl;

    private String priority;

    private boolean favorite;

    private boolean archived;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
