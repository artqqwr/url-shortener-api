package io.github.artqqwr.urlshortener.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.artqqwr.urlshortener.dto.ShortenUrlRequest;
import io.github.artqqwr.urlshortener.entity.ShortUrl;
import io.github.artqqwr.urlshortener.entity.User;
import io.github.artqqwr.urlshortener.repository.UserRepository;
import io.github.artqqwr.urlshortener.service.UrlService;
import io.github.artqqwr.urlshortener.service.UrlStatisticService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UrlController {
  
    @Autowired
    private UrlService urlService;
  
    @Autowired
    private UrlStatisticService urlStatisticService;
  
    @Autowired
    private UserRepository userRepository;
  
    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@Valid @RequestBody ShortenUrlRequest request, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ShortUrl shortUrl = urlService.shortenUrl(request.getOriginalUrl(), userOpt.get());
        return ResponseEntity.ok(shortUrl);
    }
  
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode, HttpServletRequest request) {
        Optional<ShortUrl> opt = urlService.findByShortCode(shortCode);
        if (!opt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ShortUrl shortUrl = opt.get();
      
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String referrer = request.getHeader("Referer");
      
        urlStatisticService.logClick(shortUrl, ipAddress, userAgent, referrer);
      
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(shortUrl.getOriginalUrl()))
                .build();
    }
}
