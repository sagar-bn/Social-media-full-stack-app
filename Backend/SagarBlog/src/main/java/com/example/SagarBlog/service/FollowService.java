package com.example.SagarBlog.service;

import com.example.SagarBlog.dto.FollowResponse;
import java.util.List;

public interface FollowService {
    FollowResponse followUser(String followerEmail, Long followingUserId);
    void unfollowUser(String followerEmail, Long followingUserId);
    List<FollowResponse> getFollowers(String userEmail);
    List<FollowResponse> getFollowing(String userEmail);
}
