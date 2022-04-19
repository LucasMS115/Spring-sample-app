package io.github.LucasMS115.spring_sales.service.implementation;

import io.github.LucasMS115.spring_sales.domain.repository.OrderInfos;
import io.github.LucasMS115.spring_sales.service.OrderInfoService;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoServiceImplementation implements OrderInfoService {

    private OrderInfos orderInfos;

    public OrderInfoServiceImplementation(OrderInfos orderInfos){
        this.orderInfos = orderInfos;
    }
}
