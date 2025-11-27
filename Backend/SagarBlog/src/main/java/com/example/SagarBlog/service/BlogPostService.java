package com.example.SagarBlog.service;


import com.example.SagarBlog.dto.BlogPostRequest;
import com.example.SagarBlog.dto.BlogPostResponse;
import com.example.SagarBlog.dto.CommentRequest;
import com.example.SagarBlog.dto.CommentResponse;

import java.util.List;

public interface BlogPostService {
    BlogPostResponse createPost(BlogPostRequest request, String authorEmail);
    List<BlogPostResponse> getAllPosts();
    BlogPostResponse getPostById(Long id);
    BlogPostResponse updatePost(Long id, BlogPostRequest request, String authorEmail);
    void deletePost(Long id, String requesterEmail);
    boolean isAuthor(Long postId, String email);
    CommentResponse addComment(Long postId, CommentRequest request, String commenterEmail);
    List<CommentResponse> getCommentsByPost(Long postId);
}
