package io.github.artqqwr.urlshortener.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.artqqwr.urlshortener.entity.User;
import io.github.artqqwr.urlshortener.repository.UserRepository;

@Service
public class UserService {
  
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private PasswordEncoder passwordEncoder;
  
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
