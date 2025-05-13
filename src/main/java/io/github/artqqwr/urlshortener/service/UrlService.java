package io.github.artqqwr.urlshortener.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import io.github.artqqwr.urlshortener.entity.ShortUrl;
import io.github.artqqwr.urlshortener.entity.User;
import io.github.artqqwr.urlshortener.event.ShortUrlPoolLowEvent;
import io.github.artqqwr.urlshortener.repository.ShortUrlRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UrlService {
    private final static String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
  
    private final Queue<String> availableCodes = new ConcurrentLinkedQueue<>();
    private final int PACK_SIZE = 100;
    private final int THRESHOLD = 20;
  
    @Autowired
    private ShortUrlRepository shortUrlRepository;
  
    @Autowired
    private ApplicationEventPublisher eventPublisher;
  
    @PostConstruct
    public void init() {
        refillPool();
    }
  
    public synchronized String getNewShortCode() {
        String code = availableCodes.poll();
        if (availableCodes.size() < THRESHOLD) {
            eventPublisher.publishEvent(new ShortUrlPoolLowEvent(this));
        }
        if (code == null) {
            refillPool();
            code = availableCodes.poll();
        }
        return code;
    }
  
    public synchronized void refillPool() {
        List<String> newCodes = new ArrayList<>();
        while (newCodes.size() < PACK_SIZE) {
            String code = generateRandomCode(6);
            if (!shortUrlRepository.existsByShortCode(code)) {
                newCodes.add(code);
            }
        }
        availableCodes.addAll(newCodes);
    }
  
    private String generateRandomCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
  
    public ShortUrl shortenUrl(String originalUrl, User owner) {
        String shortCode = getNewShortCode();
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(shortCode);
        shortUrl.setOwner(owner);
        return shortUrlRepository.save(shortUrl);
    }
  
    public Optional<ShortUrl> findByShortCode(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode);
    }
}
