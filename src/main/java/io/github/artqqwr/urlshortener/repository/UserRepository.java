package io.github.artqqwr.urlshortener.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.artqqwr.urlshortener.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
