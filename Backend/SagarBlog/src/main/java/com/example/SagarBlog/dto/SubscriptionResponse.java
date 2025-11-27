package com.example.SagarBlog.dto;

import com.example.SagarBlog.model.EmailSubscription;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionResponse {
    private Long id;
    private String email;
    private EmailSubscription.SubscriptionType subscriptionType;
    private Boolean isActive;
}

