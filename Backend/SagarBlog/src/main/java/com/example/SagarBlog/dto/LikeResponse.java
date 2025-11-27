package com.example.SagarBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@Builder
public class LikeResponse {
    private Long id;
    private String likedBy;
    private Long postId;
    private Long commentId;

    public LikeResponse(String postLikedSuccessfully, Long postId, String userEmail) {
    }
}
