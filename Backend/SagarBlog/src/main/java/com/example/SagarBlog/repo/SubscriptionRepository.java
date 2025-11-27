package com.example.SagarBlog.repo;

import com.example.SagarBlog.model.EmailSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<EmailSubscription, Long> {
    List<EmailSubscription> findByUserId(Long userId);
    Optional<EmailSubscription> findByEmail(String email);
    boolean existsByEmail(String email);
}
