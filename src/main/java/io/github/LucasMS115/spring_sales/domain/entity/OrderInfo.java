package io.github.LucasMS115.spring_sales.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import io.github.LucasMS115.spring_sales.domain.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    @NotNull
    private Customer customer;

    @Column(name = "order_date")
    @NotNull
    private LocalDate orderDate;

    @Column(name = "order_total_cost", scale = 2, precision = 20)
    @NotNull
    private BigDecimal orderTotalCost;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<OrderProduct> relatedProducts;

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", orderTotalCost=" + orderTotalCost +
                ", relatedProducts=" + relatedProducts +
                '}';
    }
}