package io.github.LucasMS115.spring_sales.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_total_cost", scale = 2, precision = 20)
    private BigDecimal orderTotalCost;

    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> relatedProducts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderTotalCost() {
        return orderTotalCost;
    }

    public void setOrderTotalCost(BigDecimal orderTotalCost) {
        this.orderTotalCost = orderTotalCost;
    }

    public Set<OrderProduct> getRelatedProducts() {
        return relatedProducts;
    }

    public void setRelatedProducts(Set<OrderProduct> relatedProducts) {
        this.relatedProducts = relatedProducts;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", orderTotalCost=" + orderTotalCost +
                '}';
    }
}