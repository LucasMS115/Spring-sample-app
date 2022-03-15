package io.github.LucasMS115.spring_sales.domain.entity;

public class Customer {
    private Integer id;
    private String name;

    public Customer(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Customer(String name){
        this.name = name;
    }

    public Customer(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
