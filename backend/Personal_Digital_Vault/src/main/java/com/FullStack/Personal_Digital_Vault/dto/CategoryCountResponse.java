package com.FullStack.Personal_Digital_Vault.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCountResponse
{

    private String category;

    private long count;
}
