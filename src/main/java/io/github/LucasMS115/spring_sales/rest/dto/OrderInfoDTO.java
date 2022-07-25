package io.github.LucasMS115.spring_sales.rest.dto;

import io.github.LucasMS115.spring_sales.Validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {
    @NotNull(message = "Customer id not specified")
    private Integer customer;

    @NotNull(message = "Total cost cannot be null")
    private BigDecimal totalCost;

    private String status;

    @NotEmptyList(message = "Order cannot be completed without items")
    private List<OrderedProductDTO> items;
}
