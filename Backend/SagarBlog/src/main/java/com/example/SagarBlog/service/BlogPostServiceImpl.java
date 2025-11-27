package com.example.SagarBlog.service;

import com.example.SagarBlog.dto.*;
import com.example.SagarBlog.model.*;
import com.example.SagarBlog.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public BlogPostResponse createPost(BlogPostRequest request, String authorEmail) {
        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String slug = generateSlug(request.getTitle());

        BlogPost post = BlogPost.builder()
                .title(request.getTitle())
                .slug(slug)
                .content(request.getContent())
                .featuredImageUrl(request.getFeaturedImageUrl()) // ✅ added line
                .author(author)
                .status(BlogPost.Status.DRAFT)
                .build();

        blogPostRepository.save(post);
        return mapToResponse(post);
    }

    // Add this helper method to generate slug from title
    private String generateSlug(String title) {
        if (title == null || title.trim().isEmpty()) {
            return "untitled-" + System.currentTimeMillis();
        }

        return title.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "") // Remove special characters
                .replaceAll("\\s+", "-") // Replace spaces with hyphens
                .replaceAll("-+", "-") // Replace multiple hyphens with single
                .replaceAll("^-|-$", ""); // Remove leading/trailing hyphens
    }

    @Override
    public List<BlogPostResponse> getAllPosts() {
        return blogPostRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BlogPostResponse getPostById(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToResponse(post);
    }

    @Override
    public BlogPostResponse updatePost(Long id, BlogPostRequest request, String authorEmail) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getAuthor().getEmail().equals(authorEmail)) {
            throw new AccessDeniedException("You cannot edit this post");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        blogPostRepository.save(post);
        return mapToResponse(post);
    }
    @Override
    public boolean isAuthor(Long postId, String email) {
        return blogPostRepository.findById(postId)
                .map(post -> post.getAuthor().getEmail().equals(email))
                .orElse(false);
    }


    @Override
    public void deletePost(Long id, String requesterEmail) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getAuthor().getEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("You cannot delete this post");
        }

        blogPostRepository.delete(post);
    }

    @Override
    public CommentResponse addComment(Long postId, CommentRequest request, String commenterEmail) {
        BlogPost post = blogPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User commenter = userRepository.findByEmail(commenterEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(commenter)
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .commenter(comment.getUser().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
    }
    @Override
    public List<CommentResponse> getCommentsByPost(Long postId) {
        BlogPost post = blogPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return commentRepository.findByPost(post).stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getEmail(), // This will be the commenter
                        comment.getCreatedAt() // This should be LocalDateTime
                ))
                .collect(Collectors.toList());
    }

    private BlogPostResponse mapToResponse(BlogPost post) {
        return BlogPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getUsername())
                .featuredImageUrl(
                        post.getFeaturedImageUrl() != null
                                ? post.getFeaturedImageUrl()
                                : "https://yourcdn.com/default-blog.jpg" // default fallback
                )
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(post.getComments() != null ?
                        post.getComments().stream().map(c ->
                                CommentResponse.builder()
                                        .id(c.getId())
                                        .content(c.getContent())
                                        .commenter(c.getUser().getUsername())
                                        .createdAt(c.getCreatedAt())
                                        .build()
                        ).collect(Collectors.toList()) :
                        List.of())
                .build();
    }

}

