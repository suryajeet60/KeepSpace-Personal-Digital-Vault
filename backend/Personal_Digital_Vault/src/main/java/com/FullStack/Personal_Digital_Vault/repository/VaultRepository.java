package com.FullStack.Personal_Digital_Vault.repository;

import com.FullStack.Personal_Digital_Vault.entity.User;
import com.FullStack.Personal_Digital_Vault.entity.VaultItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaultRepository extends JpaRepository<VaultItem, Long>
{

    // All items of logged-in user
    List<VaultItem> findByUser(User user);

    // Find single item by id and user
    Optional<VaultItem> findByIdAndUser(Long id, User user);

    // Find items by category
    List<VaultItem> findByUserAndCategory(User user, String category);

    // Search by title
    List<VaultItem> findByUserAndTitleContainingIgnoreCase(User user, String title);

    // Search by content
    List<VaultItem> findByUserAndContentContainingIgnoreCase(User user, String content);

    // Search by tags
    List<VaultItem> findByUserAndTagsContainingIgnoreCase(User user, String tags);

    // Global search
    List<VaultItem>
    findByUserAndTitleContainingIgnoreCaseOrUserAndContentContainingIgnoreCaseOrUserAndTagsContainingIgnoreCase(
            User user1,
            String title,
            User user2,
            String content,
            User user3,
            String tags
    );

    // Favorite items
    List<VaultItem> findByUserAndFavoriteTrue(User user);

    // Archived items
    List<VaultItem> findByUserAndArchivedTrue(User user);

    // Non archived items
    List<VaultItem> findByUserAndArchivedFalse(User user);

    // Find by priority
    List<VaultItem> findByUserAndPriority(User user, String priority);

    // Recent items
    List<VaultItem> findTop10ByUserOrderByCreatedAtDesc(User user);

    // Count by category
    long countByUserAndCategory(User user, String category);

    // Total items count
    long countByUser(User user);

    // Delete item securely
    void deleteByIdAndUser(Long id, User user);
}
