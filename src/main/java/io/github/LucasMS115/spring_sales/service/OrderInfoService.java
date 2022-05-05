package io.github.LucasMS115.spring_sales.service;

import io.github.LucasMS115.spring_sales.domain.entity.OrderInfo;
import io.github.LucasMS115.spring_sales.domain.enums.OrderStatus;
import io.github.LucasMS115.spring_sales.rest.dto.OrderInfoDTO;

import java.util.Optional;

public interface OrderInfoService {

    OrderInfo save(OrderInfoDTO dto);
    Optional<OrderInfo> getFullOrderInfo(Integer id);
    void updateStatus(Integer id, OrderStatus newStatus);
}
