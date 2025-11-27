package com.example.SagarBlog.service;

import com.example.SagarBlog.dto.SubscriptionRequest;
import com.example.SagarBlog.dto.SubscriptionResponse;
import com.example.SagarBlog.model.EmailSubscription;
import com.example.SagarBlog.repo.SubscriptionRepository;
import com.example.SagarBlog.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public SubscriptionResponse subscribe(SubscriptionRequest request) {
        EmailSubscription.SubscriptionType type;
        try {
            type = EmailSubscription.SubscriptionType.valueOf(request.getSubscriptionType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid subscription type: " + request.getSubscriptionType());
        }

        EmailSubscription sub = EmailSubscription.builder()
                .email(request.getEmail())
                .isActive(true)
                .subscriptionType(type)
                .build();

        subscriptionRepository.save(sub);

        return new SubscriptionResponse(sub.getId(), sub.getEmail(), sub.getSubscriptionType(), sub.getIsActive());
    }

    @Override
    public void unsubscribe(String email) {
        EmailSubscription sub = subscriptionRepository.findByEmail(email).orElseThrow();
        sub.setIsActive(false);
        subscriptionRepository.save(sub);
    }

    @Override
    public List<SubscriptionResponse> getAllSubscriptions() {
        return subscriptionRepository.findAll()
                .stream()
                .map(s -> new SubscriptionResponse(s.getId(), s.getEmail(), s.getSubscriptionType(), s.getIsActive()))
                .collect(Collectors.toList());
    }
}
