package com.example.SagarBlog.service;

import com.example.SagarBlog.dto.FollowResponse;
import com.example.SagarBlog.model.User;
import com.example.SagarBlog.model.UserFollow;
import com.example.SagarBlog.repo.UserFollowRepository;
import com.example.SagarBlog.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;
    private final UserFollowRepository followRepository;

    @Override
    public FollowResponse followUser(String followerEmail, Long followingUserId) {
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingUserId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new RuntimeException("Already following this user");
        }

        UserFollow follow = UserFollow.builder()
                .follower(follower)
                .following(following)
                .build();
        followRepository.save(follow);

        return new FollowResponse(
                follow.getId(),
                follower.getUsername(),
                following.getUsername(),
                "Followed successfully"
        );
    }

    @Override
    @Transactional
    public void unfollowUser(String followerEmail, Long followingUserId) {
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingUserId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        followRepository.deleteByFollowerAndFollowing(follower, following);
    }

    @Override
    public List<FollowResponse> getFollowers(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.findByFollowing(user)
                .stream()
                .map(f -> new FollowResponse(
                        f.getId(),
                        f.getFollower().getUsername(),
                        user.getUsername(),
                        "Follower"
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowResponse> getFollowing(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.findByFollower(user)
                .stream()
                .map(f -> new FollowResponse(
                        f.getId(),
                        user.getUsername(),
                        f.getFollowing().getUsername(),
                        "Following"
                ))
                .collect(Collectors.toList());
    }
}
