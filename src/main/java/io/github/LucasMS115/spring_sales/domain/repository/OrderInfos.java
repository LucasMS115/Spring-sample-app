package io.github.LucasMS115.spring_sales.domain.repository;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import io.github.LucasMS115.spring_sales.domain.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderInfos extends JpaRepository<OrderInfo, Integer> {
    Set<OrderInfo> findByCustomer(Customer customer);
}
