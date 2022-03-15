package io.github.LucasMS115.spring_sales.domain.entity;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String brand;
    private String name;
    private String description;
    private BigDecimal unityCost;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}