package com.FullStack.Personal_Digital_Vault.repository;

import com.FullStack.Personal_Digital_Vault.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check email exists
    boolean existsByEmail(String email);
}
