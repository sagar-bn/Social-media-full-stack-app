package com.example.SagarBlog.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionRequest {
    private String email;
    private String subscriptionType;
}


