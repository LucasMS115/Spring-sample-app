package io.github.LucasMS115.spring_sales.domain.repository;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import io.github.LucasMS115.spring_sales.domain.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface OrderInfos extends JpaRepository<OrderInfo, Integer> {
    //Query methods
    Set<OrderInfo> findByCustomer(Customer customer);

    @Query(" SELECT o FROM OrderInfo o LEFT JOIN FETCH o.relatedProducts WHERE o.id = :id ")
    Optional<OrderInfo> findByIdFetchItems(@Param("id") Integer id);
}
