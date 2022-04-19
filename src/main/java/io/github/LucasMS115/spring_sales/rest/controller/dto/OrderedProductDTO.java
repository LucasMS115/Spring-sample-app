package io.github.LucasMS115.spring_sales.rest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProductDTO {
    private Integer product;
    private Integer quantity;
}
