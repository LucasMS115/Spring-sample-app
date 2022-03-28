package io.github.LucasMS115.spring_sales.domain.repository;

import io.github.LucasMS115.spring_sales.domain.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProducts extends JpaRepository<OrderProduct, Integer> {
}
