package io.github.artqqwr.urlshortener.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.artqqwr.urlshortener.dto.JwtResponse;
import io.github.artqqwr.urlshortener.dto.LoginRequest;
import io.github.artqqwr.urlshortener.dto.RegisterRequest;
import io.github.artqqwr.urlshortener.entity.User;
import io.github.artqqwr.urlshortener.security.JwtTokenProvider;
import io.github.artqqwr.urlshortener.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
  
    @Autowired
    private UserService userService;
  
    @Autowired
    private AuthenticationManager authenticationManager;
  
    @Autowired
    private JwtTokenProvider tokenProvider;
  
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        User savedUser = userService.register(user);
        return ResponseEntity.ok(savedUser);
    }
  
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
      
        String token = tokenProvider.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
