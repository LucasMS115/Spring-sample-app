package io.github.LucasMS115.spring_sales.domain.entity;

import javax.persistence.*;

@Entity //makes JPA believe the class is representing a table and all its properties are columns (by default, with the same name)
@Table (name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    @Column (name = "customer_id")
    private Integer id;

    @Column (name = "name", length = 100)
    private String name;

    public Customer(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Customer(String name){
        this.name = name;
    }

    public Customer(){}

    public Integer getId() {
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
