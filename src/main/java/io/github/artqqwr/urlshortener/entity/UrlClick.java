package io.github.artqqwr.urlshortener.entity;


import java.time.LocalDateTime;

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
@Table(name = "url_clicks")
public class UrlClick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @ManyToOne
    @JoinColumn(name = "short_url_id", nullable = false)
    private ShortUrl shortUrl;
  
    @Column(name = "clicked_at", nullable = false)
    private LocalDateTime clickedAt = LocalDateTime.now();
  
    @Column(name = "ip_address")
    private String ipAddress;
  
    @Column(name = "user_agent")
    private String userAgent;
  
    @Column(name = "referrer")
    private String referrer;
}
