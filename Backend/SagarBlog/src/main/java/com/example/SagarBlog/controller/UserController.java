package com.example.SagarBlog.controller;



import com.example.SagarBlog.dto.UserProfileDTO;
import com.example.SagarBlog.model.User;
import com.example.SagarBlog.repo.UserRepository;
import com.example.SagarBlog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Adjust for your frontend URL
public class UserController {

    private final UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile(Authentication authentication) {
        // Get the username from the authenticated user
        String email = authentication.getName(); // JWT subject is email
        User user = userService.findByEmail(email); // change this line



        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Create DTO with user information
        UserProfileDTO dto = UserProfileDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .build();

        return ResponseEntity.ok(dto);
    }
    @PutMapping("/update")
    public ResponseEntity<UserProfileDTO> updateUserProfile(
            Authentication authentication,
            @RequestBody UserProfileDTO updatedProfile
    ) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Update editable fields
        user.setUsername(updatedProfile.getUsername());
        user.setFirstName(updatedProfile.getFirstName());
        user.setLastName(updatedProfile.getLastName());
        user.setAvatarUrl(updatedProfile.getAvatarUrl());
        user.setBio(updatedProfile.getBio());

        // Save changes
        User savedUser = userService.save(user);

        UserProfileDTO dto = UserProfileDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .avatarUrl(savedUser.getAvatarUrl())
                .bio(savedUser.getBio())
                .role(savedUser.getRole().name())
                .createdAt(savedUser.getCreatedAt())
                .build();

        return ResponseEntity.ok(dto);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> getUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("username", user.getUsername());
        userData.put("email", user.getEmail());

        return ResponseEntity.ok(userData);
    }


}