package io.github.artqqwr.urlshortener.event;

import org.springframework.context.ApplicationEvent;

public class ShortUrlPoolLowEvent extends ApplicationEvent {
    public ShortUrlPoolLowEvent(Object source) {
        super(source);
    }
}
