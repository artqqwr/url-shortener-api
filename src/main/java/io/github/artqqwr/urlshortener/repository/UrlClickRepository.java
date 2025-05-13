package io.github.artqqwr.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.artqqwr.urlshortener.entity.UrlClick;

public interface UrlClickRepository extends JpaRepository<UrlClick, Long> {
}
