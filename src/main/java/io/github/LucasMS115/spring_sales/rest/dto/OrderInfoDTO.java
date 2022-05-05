package io.github.LucasMS115.spring_sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {
    private Integer customer;
    private BigDecimal totalCost;
    private String status;
    private List<OrderedProductDTO> items;
}
