package io.github.artqqwr.urlshortener.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.github.artqqwr.urlshortener.entity.ShortUrl;
import io.github.artqqwr.urlshortener.entity.UrlClick;
import io.github.artqqwr.urlshortener.repository.ShortUrlRepository;
import io.github.artqqwr.urlshortener.repository.UrlClickRepository;

@Service
public class UrlStatisticService {
  
    @Autowired
    private UrlClickRepository urlClickRepository;
  
    @Autowired
    private ShortUrlRepository shortUrlRepository;
  
    @Async
    public void logClick(ShortUrl shortUrl, String ipAddress, String userAgent, String referrer) {
        UrlClick click = new UrlClick();
        click.setShortUrl(shortUrl);
        click.setIpAddress(ipAddress);
        click.setUserAgent(userAgent);
        click.setReferrer(referrer);
        urlClickRepository.save(click);
      
        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        shortUrlRepository.save(shortUrl);
    }
}
