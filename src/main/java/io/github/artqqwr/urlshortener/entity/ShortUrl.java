package io.github.artqqwr.urlshortener.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "short_urls")
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Column(nullable = false, unique = true)
    private String shortCode;
  
    @Column(nullable = false)
    private String originalUrl;
  
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
  
    @Column(nullable = false)
    private Long clickCount = 0L;
}
