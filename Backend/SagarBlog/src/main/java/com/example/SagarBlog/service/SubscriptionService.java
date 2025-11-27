package com.example.SagarBlog.service;

import com.example.SagarBlog.dto.SubscriptionRequest;
import com.example.SagarBlog.dto.SubscriptionResponse;
import java.util.List;

public interface SubscriptionService {
    SubscriptionResponse subscribe(SubscriptionRequest request);
    void unsubscribe(String email);
    List<SubscriptionResponse> getAllSubscriptions();
}
