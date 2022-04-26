package io.github.LucasMS115.spring_sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductResponseDTO {
    private String brand;
    private String name;
    private String description;
    private BigDecimal unityCost;
    private Integer quantity;
}
