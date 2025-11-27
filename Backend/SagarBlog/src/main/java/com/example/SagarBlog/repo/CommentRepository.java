package com.example.SagarBlog.repo;

import com.example.SagarBlog.model.BlogPost;
import com.example.SagarBlog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByPost(BlogPost post);
}
