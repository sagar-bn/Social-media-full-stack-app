package com.example.SagarBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowResponse {
    private Long id;
    private String followerUsername;
    private String followingUsername;
    private String message;
}
