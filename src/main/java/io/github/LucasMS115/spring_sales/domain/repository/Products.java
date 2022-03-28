package io.github.LucasMS115.spring_sales.domain.repository;

import io.github.LucasMS115.spring_sales.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Products extends JpaRepository<Product, Integer> {
}
