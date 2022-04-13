package io.github.LucasMS115.spring_sales.domain.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnityCost() {
        return unityCost;
    }

    public void setUnityCost(BigDecimal unityCost) {
        this.unityCost = unityCost;
    }

    public Set<OrderProduct> getRelatedOrders() {
        return relatedOrders;
    }

    public void setRelatedOrders(Set<OrderProduct> relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

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