package com.example.SagarBlog.service;

import java.util.List;
import java.util.Map;

public interface LikeService {
    String togglePostLike(Long postId, String email);
    Long getPostLikeCount(Long postId);
    List<Map<String, String>> getUsersWhoLikedPost(Long postId);
    String toggleCommentLike(Long commentId, String email);
    void likePost(Long postId, String email);
    void likeComment(Long commentId, String email);
}
