package io.github.LucasMS115.spring_sales.domain.repository;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import io.github.LucasMS115.spring_sales.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Products extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM Product c WHERE c.name LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    List<Customer> findByNameLike(@Param("name") String name);

    @Query(value = "SELECT * FROM Product c WHERE c.brand LIKE CONCAT('%',:brand,'%')", nativeQuery = true)
    List<Customer> findByBrandLike(@Param("brand") String brand);
}
