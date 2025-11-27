package com.example.SagarBlog.controller;
import com.example.SagarBlog.dto.SubscriptionRequest;
import com.example.SagarBlog.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<String> subscribe(@RequestBody SubscriptionRequest request) {
        subscriptionService.subscribe(request);
        return ResponseEntity.ok("Subscribed successfully!");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> unsubscribe(@PathVariable String email) {
        subscriptionService.unsubscribe(email);
        return ResponseEntity.ok("Unsubscribed successfully!");
    }
}
