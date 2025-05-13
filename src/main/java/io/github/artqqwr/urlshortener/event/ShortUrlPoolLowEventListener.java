package io.github.artqqwr.urlshortener.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import io.github.artqqwr.urlshortener.service.UrlService;

@Component
public class ShortUrlPoolLowEventListener {
  
    @Autowired
    private UrlService urlService;
  
    @Async
    @EventListener
    public void handleShortUrlPoolLowEvent(ShortUrlPoolLowEvent event) {
        urlService.refillPool();
    }
}
