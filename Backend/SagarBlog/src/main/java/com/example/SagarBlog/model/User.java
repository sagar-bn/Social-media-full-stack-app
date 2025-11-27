package com.example.SagarBlog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

    @Entity
    @Table(name = "users")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, length = 100)
        private String username;

        @Column(nullable = false, unique = true, length = 150)
        private String email;

        @Column(nullable = false)
        private String password;

        private String firstName;
        private String lastName;

        @Column(columnDefinition = "TEXT")
        private String bio;

        private String avatarUrl;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        private Role role;

        private Boolean isActive = true;
        private Boolean emailVerified = false;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;

        private LocalDateTime lastLogin;

        @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
        private List<BlogPost> blogPosts;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Comment> comments;

        // ✅ Return authority based on enum role
        public Collection<? extends GrantedAuthority> getRoles() {
            return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }
        public enum Role {
            ADMIN,
            AUTHOR,
            USER
        }


    }


