package com.example.SagarBlog.controller;

import com.example.SagarBlog.dto.FollowResponse;
import com.example.SagarBlog.model.User;
import com.example.SagarBlog.repo.UserRepository;
import com.example.SagarBlog.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;

    // Follow user by username (from frontend)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/follow")
    public ResponseEntity<FollowResponse> followUser(@RequestBody Map<String, String> body, Authentication auth) {
        String followerEmail = auth.getName(); // logged-in user (email)
        String authorUsername = body.get("author");

        User followingUser = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FollowResponse response = followService.followUser(followerEmail, followingUser.getId());
        return ResponseEntity.ok(response);
    }

    // Unfollow user
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody Map<String, String> body, Authentication auth) {
        String followerEmail = auth.getName();
        String authorUsername = body.get("author");

        User followingUser = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        followService.unfollowUser(followerEmail, followingUser.getId());
        return ResponseEntity.ok("Unfollowed successfully");
    }

    // Get followers of logged-in user
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/followers")
    public ResponseEntity<List<FollowResponse>> getFollowers(Authentication auth) {
        List<FollowResponse> followers = followService.getFollowers(auth.getName());
        return ResponseEntity.ok(followers);
    }

    // Get following list of logged-in user
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/following")
    public ResponseEntity<List<FollowResponse>> getFollowing(Authentication auth) {
        List<FollowResponse> following = followService.getFollowing(auth.getName());
        return ResponseEntity.ok(following);
    }


}
