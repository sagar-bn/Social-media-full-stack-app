package com.example.SagarBlog.service;

import com.example.SagarBlog.model.*;
import com.example.SagarBlog.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostLikeRepository postLikeRepository;
    private final BlogPostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public String togglePostLike(Long postId, String email) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (postLikeRepository.existsByPostAndUser(post, user)) {
            postLikeRepository.deleteByPostAndUser(post, user);
            return "Unliked";
        } else {
            PostLike like = PostLike.builder()
                    .post(post)
                    .user(user)
                    .build();
            postLikeRepository.save(like);
            return "Liked";
        }
    }

    @Override
    public Long getPostLikeCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    @Override
    public List<Map<String, String>> getUsersWhoLikedPost(Long postId) {
        return postLikeRepository.findByPostId(postId).stream()
                .map(like -> {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", like.getUser().getUsername());
                    data.put("email", like.getUser().getEmail());
                    return data;
                })
                .toList();
    }

    @Override
    public String toggleCommentLike(Long commentId, String email) {
        // future feature for comment likes
        return "Liked Comment (to be implemented)";
    }

    @Override
    public void likePost(Long postId, String email) {
        togglePostLike(postId, email);
    }

    @Override
    public void likeComment(Long commentId, String email) {
        toggleCommentLike(commentId, email);
    }
}
