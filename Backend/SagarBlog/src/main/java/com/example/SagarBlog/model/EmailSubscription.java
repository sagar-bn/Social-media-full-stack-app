package com.example.SagarBlog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;



    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime unsubscribedAt;

    public enum SubscriptionType {
        NEWSLETTER,
        PROMOTIONS,
        UPDATES
    }
}
