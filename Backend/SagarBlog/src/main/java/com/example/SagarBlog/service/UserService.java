package com.example.SagarBlog.service;



import com.example.SagarBlog.model.User;
import com.example.SagarBlog.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));
    }

    public User save(User user) {
        return userRepository.save(user);

    }

    // Your other methods...
}