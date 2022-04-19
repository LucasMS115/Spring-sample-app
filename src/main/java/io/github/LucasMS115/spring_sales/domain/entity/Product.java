package io.github.LucasMS115.spring_sales.domain.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "unity_cost")
    private BigDecimal unityCost;

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> relatedOrders;

}