package com.FullStack.Personal_Digital_Vault.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsResponse
{

    private long totalItems;

    private long totalCategories;

    private long favoriteItems;

    private long archivedItems;

    private String mostUsedCategory;

    private String latestSavedItem;
}
