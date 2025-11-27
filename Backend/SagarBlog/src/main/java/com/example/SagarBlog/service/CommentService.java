package com.example.SagarBlog.service;


import com.example.SagarBlog.dto.CommentRequest;
import com.example.SagarBlog.dto.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(Long postId, CommentRequest request, String commenterEmail);
    List<CommentResponse> getCommentsByPost(Long postId);
    void deleteComment(Long commentId, String requesterEmail);
}
