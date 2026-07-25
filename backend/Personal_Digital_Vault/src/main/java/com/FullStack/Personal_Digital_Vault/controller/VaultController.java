package com.FullStack.Personal_Digital_Vault.controller;

import com.FullStack.Personal_Digital_Vault.dto.*;
import com.FullStack.Personal_Digital_Vault.service.VaultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vault")
@RequiredArgsConstructor

public class VaultController
{

    private final VaultService vaultService;

    // CREATE ITEM
    // POST: /api/vault
    @PostMapping
    public ResponseEntity<VaultResponse> createItem(
            @Valid @RequestBody VaultRequest request
    ) {

        VaultResponse response = vaultService.createItem(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // GET ALL ITEMS
    // GET: /api/vault
    @GetMapping
    public ResponseEntity<List<VaultResponse>> getAllItems()
    {
        return ResponseEntity.ok(vaultService.getAllItems());
    }

    // GET ITEM BY ID
    // GET: /api/vault/{id}
    @GetMapping("/{id}")
    public ResponseEntity<VaultResponse> getItemById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(vaultService.getItemById(id));
    }

    // UPDATE ITEM
    // PUT: /api/vault/{id}
    @PutMapping("/{id}")
    public ResponseEntity<VaultResponse> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody VaultRequest request
    ) {

        return ResponseEntity.ok(vaultService.updateItem(id, request));
    }

    // DELETE ITEM
    // DELETE: /api/vault/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(
            @PathVariable Long id
    ) {

        vaultService.deleteItem(id);

        return ResponseEntity.ok("Vault item deleted successfully");
    }

    // SEARCH ITEMS
    // GET: /api/vault/search?keyword=spring
    @GetMapping("/search")
    public ResponseEntity<List<SearchResponse>> searchItems(
            @RequestParam String keyword
    ) {

        return ResponseEntity.ok(vaultService.searchItems(keyword));
    }

    // GET ITEMS BY CATEGORY
    // GET: /api/vault/category/{category}
    @GetMapping("/category/{category}")
    public ResponseEntity<List<VaultResponse>> getItemsByCategory(
            @PathVariable String category
    ) {

        return ResponseEntity.ok(vaultService.getItemsByCategory(category));
    }

    // GET FAVORITE ITEMS
    // GET: /api/vault/favorites
    @GetMapping("/favorites")
    public ResponseEntity<List<VaultResponse>> getFavoriteItems()
    {
        return ResponseEntity.ok(vaultService.getFavoriteItems());
    }

    // GET ARCHIVED ITEMS
    // GET: /api/vault/archived
    @GetMapping("/archived")
    public ResponseEntity<List<VaultResponse>> getArchivedItems()
    {
        return ResponseEntity.ok(vaultService.getArchivedItems());
    }

    // GET ACTIVE ITEMS
    // GET: /api/vault/active
    @GetMapping("/active")
    public ResponseEntity<List<VaultResponse>> getActiveItems()
    {
        return ResponseEntity.ok(vaultService.getActiveItems());
    }

    // GET RECENT ITEMS
    // GET: /api/vault/recent
    @GetMapping("/recent")
    public ResponseEntity<List<VaultResponse>> getRecentItems()
    {
        return ResponseEntity.ok(vaultService.getRecentItems());
    }

    // GET ANALYTICS
    // GET: /api/vault/analytics
    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsResponse> getAnalytics()
    {
        return ResponseEntity.ok(vaultService.getAnalytics());
    }

    // GET CATEGORY STATISTICS
    // GET: /api/vault/category-stats
    @GetMapping("/category-stats")
    public ResponseEntity<List<CategoryCountResponse>> getCategoryStatistics()
    {
        return ResponseEntity.ok(vaultService.getCategoryStatistics());
    }
}
