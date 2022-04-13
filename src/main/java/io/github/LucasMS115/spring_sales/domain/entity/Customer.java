package io.github.LucasMS115.spring_sales.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Set;

@Entity //makes JPA believe the class is representing a table and all its properties are columns (by default, with the same name)
@Table (name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    @Column (name = "customer_id")
    private Integer id;

    @Column (name = "name", length = 100)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE) // mappedBy = <name of the property used to refer the customer at the other entity>
    @JsonIgnore
    @JsonManagedReference
    private Set<OrderInfo> orders;

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

    public Set<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderInfo> orders) {
        this.orders = orders;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
