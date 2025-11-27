package com.example.SagarBlog.repo;

import com.example.SagarBlog.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
    Long countByPostId(Long postId);

    boolean existsByPostAndUser(BlogPost post, User user);

    void deleteByPostAndUser(BlogPost post, User user);
    List<PostLike> findByPostId(Long postId);
}

