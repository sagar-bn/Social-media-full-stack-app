package com.example.SagarBlog.controller;

import com.example.SagarBlog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // ✅ Toggle like/unlike post
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/posts/{postId}")
    public ResponseEntity<String> togglePostLike(@PathVariable Long postId, Authentication auth) {
        String response = likeService.togglePostLike(postId, auth.getName());
        return ResponseEntity.ok(response);
    }

    // ✅ Toggle like/unlike comment
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<String> toggleCommentLike(@PathVariable Long commentId, Authentication auth) {
        String response = likeService.toggleCommentLike(commentId, auth.getName());
        return ResponseEntity.ok(response);
    }

    // ✅ Get total post likes count
    @GetMapping("/posts/{postId}/count")
    public ResponseEntity<Long> getPostLikeCount(@PathVariable Long postId) {
        Long count = likeService.getPostLikeCount(postId);
        return ResponseEntity.ok(count);
    }

    // ✅ Get users who liked the post
    @GetMapping("/posts/{postId}/users")
    public ResponseEntity<List<Map<String, String>>> getUsersWhoLikedPost(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.getUsersWhoLikedPost(postId));
    }
}
