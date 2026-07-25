package com.FullStack.Personal_Digital_Vault.service.impl;

import com.FullStack.Personal_Digital_Vault.dto.*;
import com.FullStack.Personal_Digital_Vault.entity.User;
import com.FullStack.Personal_Digital_Vault.entity.VaultItem;
import com.FullStack.Personal_Digital_Vault.exception.ResourceNotFoundException;
import com.FullStack.Personal_Digital_Vault.repository.UserRepository;
import com.FullStack.Personal_Digital_Vault.repository.VaultRepository;
import com.FullStack.Personal_Digital_Vault.service.VaultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaultServiceImpl implements VaultService
{

    private final VaultRepository vaultRepository;

    private final UserRepository userRepository;

    // GET CURRENT LOGGED-IN USER
    private User getCurrentUser()
    {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // CREATE ITEM
    @Override
    public VaultResponse createItem(VaultRequest request)
    {

        User currentUser = getCurrentUser();

        VaultItem item = VaultItem.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .tags(request.getTags())
                .referenceUrl(request.getReferenceUrl())
                .priority(request.getPriority())
                .favorite(request.isFavorite())
                .archived(request.isArchived())
                .user(currentUser)
                .build();

        VaultItem savedItem = vaultRepository.save(item);

        return mapToResponse(savedItem);
    }

    // GET ALL ITEMS
    @Override
    public List<VaultResponse> getAllItems()
    {

        User currentUser = getCurrentUser();

        return vaultRepository.findByUser(currentUser)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET ITEM BY ID
    @Override
    public VaultResponse getItemById(Long id)
    {

        User currentUser = getCurrentUser();

        VaultItem item = vaultRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vault item not found with id: " + id
                        ));

        return mapToResponse(item);
    }

    // UPDATE ITEM
    @Override
    public VaultResponse updateItem(Long id, VaultRequest request)
    {

        User currentUser = getCurrentUser();

        VaultItem item = vaultRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vault item not found with id: " + id
                        ));

        item.setTitle(request.getTitle());
        item.setContent(request.getContent());
        item.setCategory(request.getCategory());
        item.setTags(request.getTags());
        item.setReferenceUrl(request.getReferenceUrl());
        item.setPriority(request.getPriority());
        item.setFavorite(request.isFavorite());
        item.setArchived(request.isArchived());

        VaultItem updatedItem = vaultRepository.save(item);

        return mapToResponse(updatedItem);
    }

    // DELETE ITEM
    @Override
    public void deleteItem(Long id)
    {

        User currentUser = getCurrentUser();

        VaultItem item = vaultRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vault item not found with id: " + id
                        ));

        vaultRepository.delete(item);
    }

    // SEARCH ITEMS
    @Override
    public List<SearchResponse> searchItems(String keyword)
    {

        User currentUser = getCurrentUser();

        List<VaultItem> items =
                vaultRepository
                        .findByUserAndTitleContainingIgnoreCaseOrUserAndContentContainingIgnoreCaseOrUserAndTagsContainingIgnoreCase(
                                currentUser,
                                keyword,
                                currentUser,
                                keyword,
                                currentUser,
                                keyword
                        );

        return items.stream()
                .map(this::mapToSearchResponse)
                .collect(Collectors.toList());
    }

    // GET ITEMS BY CATEGORY
    @Override
    public List<VaultResponse> getItemsByCategory(String category)
    {

        User currentUser = getCurrentUser();

        return vaultRepository
                .findByUserAndCategory(currentUser, category)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // FAVORITE ITEMS
    @Override
    public List<VaultResponse> getFavoriteItems()
    {

        User currentUser = getCurrentUser();

        return vaultRepository
                .findByUserAndFavoriteTrue(currentUser)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ARCHIVED ITEMS
    @Override
    public List<VaultResponse> getArchivedItems()
    {

        User currentUser = getCurrentUser();

        return vaultRepository
                .findByUserAndArchivedTrue(currentUser)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ACTIVE ITEMS
    @Override
    public List<VaultResponse> getActiveItems()
    {

        User currentUser = getCurrentUser();

        return vaultRepository
                .findByUserAndArchivedFalse(currentUser)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // RECENT ITEMS
    @Override
    public List<VaultResponse> getRecentItems()
    {

        User currentUser = getCurrentUser();

        return vaultRepository
                .findTop10ByUserOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ANALYTICS
    @Override
    public AnalyticsResponse getAnalytics()
    {

        User currentUser = getCurrentUser();

        List<VaultItem> allItems = vaultRepository.findByUser(currentUser);

        long totalItems = allItems.size();

        long totalCategories = allItems.stream()
                .map(VaultItem::getCategory)
                .filter(Objects::nonNull)
                .distinct()
                .count();

        long favoriteItems = allItems.stream()
                .filter(VaultItem::isFavorite)
                .count();

        long archivedItems = allItems.stream()
                .filter(VaultItem::isArchived)
                .count();

        String mostUsedCategory = allItems.stream()
                .filter(item -> item.getCategory() != null)
                .collect(Collectors.groupingBy(
                        VaultItem::getCategory,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        String latestSavedItem = allItems.stream()
                .max(Comparator.comparing(
                        VaultItem::getCreatedAt
                ))
                .map(VaultItem::getTitle)
                .orElse("N/A");

        return AnalyticsResponse.builder()
                .totalItems(totalItems)
                .totalCategories(totalCategories)
                .favoriteItems(favoriteItems)
                .archivedItems(archivedItems)
                .mostUsedCategory(mostUsedCategory)
                .latestSavedItem(latestSavedItem)
                .build();
    }

    // CATEGORY STATISTICS
    @Override
    public List<CategoryCountResponse> getCategoryStatistics()
    {

        User currentUser = getCurrentUser();

        List<VaultItem> items = vaultRepository.findByUser(currentUser);

        Map<String, Long> categoryMap =
                items.stream()
                        .filter(item ->
                                item.getCategory() != null
                        )
                        .collect(Collectors.groupingBy(
                                VaultItem::getCategory,
                                Collectors.counting()
                        ));

        return categoryMap.entrySet()
                .stream()
                .map(entry ->
                        CategoryCountResponse.builder()
                                .category(entry.getKey())
                                .count(entry.getValue())
                                .build()
                )
                .collect(Collectors.toList());
    }

    // MAP ENTITY TO RESPONSE DTO
    private VaultResponse mapToResponse(VaultItem item)
    {

        return VaultResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .content(item.getContent())
                .category(item.getCategory())
                .tags(item.getTags())
                .referenceUrl(item.getReferenceUrl())
                .priority(item.getPriority())
                .favorite(item.isFavorite())
                .archived(item.isArchived())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    // MAP ENTITY TO SEARCH RESPONSE DTO
    private SearchResponse mapToSearchResponse(VaultItem item)
    {

        return SearchResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .category(item.getCategory())
                .tags(item.getTags())
                .favorite(item.isFavorite())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
