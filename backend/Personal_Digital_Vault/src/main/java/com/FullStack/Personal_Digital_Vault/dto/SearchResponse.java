package com.FullStack.Personal_Digital_Vault.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse
{

    private Long id;

    private String title;

    private String category;

    private String tags;

    private boolean favorite;

    private LocalDateTime createdAt;
}
