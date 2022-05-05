package io.github.LucasMS115.spring_sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfoResponseDTO {
    private Integer id;
    private String customerName;
    private BigDecimal totalCost;
    private String orderDate;
    private String status;
    private Set<OrderProductResponseDTO> products;
}
