package io.github.artqqwr.urlshortener.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
  
    @Bean
    OpenAPI customOpenAPI() {
       return new OpenAPI()
                .info(new Info()
                      .title("URL Shortener API")
                      .version("1.0")
                      .description("Documentation for URL Shortener API"));
    }
}
