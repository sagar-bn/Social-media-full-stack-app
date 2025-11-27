package com.example.SagarBlog.service;


import com.example.SagarBlog.dto.CommentRequest;
import com.example.SagarBlog.dto.CommentResponse;
import com.example.SagarBlog.model.BlogPost;
import com.example.SagarBlog.model.Comment;
import com.example.SagarBlog.model.User;
import com.example.SagarBlog.repo.BlogPostRepository;
import com.example.SagarBlog.repo.CommentRepository;
import com.example.SagarBlog.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponse addComment(Long postId, CommentRequest request, String commenterEmail) {
        BlogPost post = blogPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User commenter = userRepository.findByEmail(commenterEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(commenter)
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        return mapToResponse(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId, String requesterEmail) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getEmail().equals(requesterEmail)
                && !comment.getPost().getAuthor().getEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("You cannot delete this comment");
        }

        commentRepository.delete(comment);
    }

    private CommentResponse mapToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .commenter(comment.getUser().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
