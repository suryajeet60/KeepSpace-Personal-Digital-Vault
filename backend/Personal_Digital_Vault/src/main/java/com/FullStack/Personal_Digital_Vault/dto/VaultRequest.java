package com.FullStack.Personal_Digital_Vault.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaultRequest
{

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 10000, message = "Content is too large")
    private String content;

    @Size(max = 100, message = "Category cannot exceed 100 characters")
    private String category;

    @Size(max = 500, message = "Tags cannot exceed 500 characters")
    private String tags;

    @Size(max = 1000, message = "Reference URL cannot exceed 1000 characters")
    private String referenceUrl;

    private String priority;

    private boolean favorite;

    private boolean archived;
}
