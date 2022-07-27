package io.github.LucasMS115.spring_sales.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "brand")
    @NotEmpty(message = "Product brand not specified")
    private String brand;

    @Column(name = "name")
    @NotEmpty(message = "Product name not specified")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @Column(name = "unity_cost")
    @NotNull(message = "Unity cost not specified")
    private BigDecimal unityCost;

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> relatedOrders;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unityCost=" + unityCost +
                '}';
    }
}