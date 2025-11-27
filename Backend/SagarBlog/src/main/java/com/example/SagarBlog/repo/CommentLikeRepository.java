package com.example.SagarBlog.repo;

import com.example.SagarBlog.model.Comment;
import com.example.SagarBlog.model.CommentLike;
import com.example.SagarBlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
    Long countByCommentId(Long commentId);

    boolean existsByCommentAndUser(Comment comment, User user);

    void deleteByCommentAndUser(Comment comment, User user);
}
