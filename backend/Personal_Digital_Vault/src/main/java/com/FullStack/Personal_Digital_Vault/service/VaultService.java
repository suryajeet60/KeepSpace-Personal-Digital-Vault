package com.FullStack.Personal_Digital_Vault.service;

import com.FullStack.Personal_Digital_Vault.dto.*;

import java.util.List;

public interface VaultService
{
    // Create item
    VaultResponse createItem(VaultRequest request);

    // Get all items
    List<VaultResponse> getAllItems();

    // Get item by id
    VaultResponse getItemById(Long id);

    // Update item
    VaultResponse updateItem(Long id, VaultRequest request);

    // Delete item
    void deleteItem(Long id);

    // Search items
    List<SearchResponse> searchItems(String keyword);

    // Get items by category
    List<VaultResponse> getItemsByCategory(String category);

    // Favorite items
    List<VaultResponse> getFavoriteItems();

    // Archived items
    List<VaultResponse> getArchivedItems();

    // Non archived items
    List<VaultResponse> getActiveItems();

    // Recent items
    List<VaultResponse> getRecentItems();

    // Analytics
    AnalyticsResponse getAnalytics();

    // Category statistics
    List<CategoryCountResponse> getCategoryStatistics();
}
