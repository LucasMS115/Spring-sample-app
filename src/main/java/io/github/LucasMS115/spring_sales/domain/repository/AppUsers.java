package io.github.LucasMS115.spring_sales.domain.repository;

import io.github.LucasMS115.spring_sales.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUsers extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
