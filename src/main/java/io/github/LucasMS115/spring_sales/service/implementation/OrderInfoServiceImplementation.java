package io.github.LucasMS115.spring_sales.service.implementation;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import io.github.LucasMS115.spring_sales.domain.entity.OrderInfo;
import io.github.LucasMS115.spring_sales.domain.entity.OrderProduct;
import io.github.LucasMS115.spring_sales.domain.entity.Product;
import io.github.LucasMS115.spring_sales.domain.repository.Customers;
import io.github.LucasMS115.spring_sales.domain.repository.OrderInfos;
import io.github.LucasMS115.spring_sales.domain.repository.OrderProducts;
import io.github.LucasMS115.spring_sales.domain.repository.Products;
import io.github.LucasMS115.spring_sales.exception.BusinessRulesException;
import io.github.LucasMS115.spring_sales.rest.controller.dto.OrderInfoDTO;
import io.github.LucasMS115.spring_sales.rest.controller.dto.OrderedProductDTO;
import io.github.LucasMS115.spring_sales.service.OrderInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //create a constructor with all the "final" properties
public class OrderInfoServiceImplementation implements OrderInfoService {

    private final OrderInfos orderInfos;
    private final Customers customers;
    private final Products products;
    private final OrderProducts orderProducts;


    @Override
    @Transactional
    public OrderInfo save(OrderInfoDTO dto) {

        Customer customer = customers.findById(dto.getCustomer()).orElseThrow( () -> new BusinessRulesException("Invalid customer"));

        OrderInfo order = new OrderInfo();
        order.setOrderDate(LocalDate.now());
        order.setOrderTotalCost(dto.getTotalCost());
        order.setCustomer(customer);

        List<OrderProduct> orderProduct = convertItems(order, dto.getItems());
        orderInfos.save(order);
        orderProducts.saveAll(orderProduct);

        return order;
    }

    private List<OrderProduct> convertItems(OrderInfo order, List<OrderedProductDTO> items){
        if(items.isEmpty()) {
            throw new BusinessRulesException("Can't create an order without products");
        }

        return items
                .stream()
                .map( dto -> {
                    Product product = products
                            .findById(dto.getProduct())
                            .orElseThrow(() -> new BusinessRulesException( "Invalid product id: " + dto.getProduct() ));

                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setQuantity(dto.getQuantity());
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(product);
                    return orderProduct;
                }).collect(Collectors.toList());
    }
}
