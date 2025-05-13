package io.github.artqqwr.urlshortener.dto;


import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenUrlRequest {
    @NotBlank
    @URL
    private String originalUrl;
}
