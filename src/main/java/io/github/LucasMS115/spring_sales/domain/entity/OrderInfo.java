package io.github.LucasMS115.spring_sales.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private Integer id;
    private Customer customer;
    private Date orderDate;
    private BigDecimal orderTotalCost;

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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderTotalCost() {
        return orderTotalCost;
    }

    public void setOrderTotalCost(BigDecimal orderTotalCost) {
        this.orderTotalCost = orderTotalCost;
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