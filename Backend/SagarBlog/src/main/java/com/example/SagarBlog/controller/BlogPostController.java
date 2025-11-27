package com.example.SagarBlog.controller;


import com.example.SagarBlog.dto.BlogPostRequest;
import com.example.SagarBlog.dto.BlogPostResponse;
import com.example.SagarBlog.dto.CommentRequest;
import com.example.SagarBlog.dto.CommentResponse;
import com.example.SagarBlog.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PreAuthorize("hasAnyRole('USER','AUTHOR')")
    @PostMapping
    public ResponseEntity<BlogPostResponse> createPost(@RequestBody BlogPostRequest request, Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(blogPostService.createPost(request,email));
    }

    @GetMapping("/public/")
    public ResponseEntity<List<BlogPostResponse>> getAllPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    // ADD THIS NEW PUBLIC ENDPOINT FOR SINGLE POSTS
    @GetMapping("/public/{id}")
    public ResponseEntity<BlogPostResponse> getPublicPostById(@PathVariable Long id) {
        return ResponseEntity.ok(blogPostService.getPostById(id));
    }

    // KEEP THE EXISTING AUTHENTICATED ENDPOINT (for admin/author access)
    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(blogPostService.getPostById(id));
    }

    @PreAuthorize("hasAnyRole('USER','AUTHOR')")
    @PutMapping("/{id}")
    public ResponseEntity<BlogPostResponse> updatePost(@PathVariable Long id, @RequestBody BlogPostRequest request , Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(blogPostService.updatePost(id, request ,email));
    }

    @PreAuthorize("hasRole('ADMIN') or @blogPostServiceImpl.isAuthor(#id, authentication.name)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id ,Authentication authentication) {
        String email = authentication.getName();
        blogPostService.deletePost(id,email);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PreAuthorize("hasAnyRole('USER','AUTHOR')")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request,
            Authentication authentication) {

        String commenterEmail = authentication.getName();
        return ResponseEntity.ok(blogPostService.addComment(postId, request, commenterEmail));
    }

    // ADD THIS NEW PUBLIC ENDPOINT FOR VIEWING COMMENTS
    @GetMapping("/public/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getPublicComments(@PathVariable Long postId) {
        return ResponseEntity.ok(blogPostService.getCommentsByPost(postId));
    }

    // KEEP THE EXISTING AUTHENTICATED ENDPOINT (might be used for moderation)
    @PreAuthorize("hasAnyRole('USER','AUTHOR')")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(blogPostService.getCommentsByPost(postId));
    }
}