package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.OrderInfo;
import io.github.LucasMS115.spring_sales.domain.entity.OrderProduct;
import io.github.LucasMS115.spring_sales.domain.enums.OrderStatus;
import io.github.LucasMS115.spring_sales.domain.repository.OrderInfos;
import io.github.LucasMS115.spring_sales.domain.repository.Customers;
import io.github.LucasMS115.spring_sales.rest.dto.OrderInfoDTO;
import io.github.LucasMS115.spring_sales.rest.dto.OrderInfoResponseDTO;
import io.github.LucasMS115.spring_sales.rest.dto.OrderProductResponseDTO;
import io.github.LucasMS115.spring_sales.rest.dto.UpdateOrderStatusDTO;
import io.github.LucasMS115.spring_sales.service.OrderInfoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/api/orders")
public class OrderInfoController {

    private final OrderInfos orders;
    private final OrderInfoService orderInfoService; //uses the interface so it can be filled with distinct implementations of the class (like a mocked one for example)
    private final Customers customers;

    public OrderInfoController(OrderInfos orders, OrderInfoService orderInfoService, Customers customers) {
        this.orders = orders;
        this.orderInfoService = orderInfoService;
        this.customers = customers;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello - Orders";
    }

    @GetMapping()
    public List<OrderInfo> list(){
        return orders.findAll();
    }

//    @GetMapping("/{id}")
//    public OrderInfo getById(@PathVariable("id") Integer id) {
//        return orders
//                .findById(id)
//                .orElseThrow( () ->
//                        new ResponseStatusException(NOT_FOUND, "Order not found")
//                );
//    }

    @GetMapping("/{id}")
    public OrderInfoResponseDTO getById(@PathVariable Integer id){
        return orderInfoService.getFullOrderInfo(id)
                .map(this::convert)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Order not found"));
    }

    @GetMapping("/find")
    public List<OrderInfo> find(OrderInfo filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<OrderInfo> example = Example.of(filter, matcher);
        return orders.findAll(example);
    }

    private OrderInfoResponseDTO convert(OrderInfo order){
        return OrderInfoResponseDTO
                .builder()
                .id(order.getId())
                .customerName(order.getCustomer().getName())
                .totalCost(order.getOrderTotalCost())
                .orderDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .status(order.getStatus().name())
                .products(convert(order.getRelatedProducts()))
                .build();
    }

    private Set<OrderProductResponseDTO> convert(Set<OrderProduct> items){
        if(CollectionUtils.isEmpty(items)) {
            return Collections.emptySet();
        }

        return items.stream().map(item -> OrderProductResponseDTO
                .builder()
                .brand(item.getProduct().getBrand())
                .name(item.getProduct().getName())
                .description(item.getProduct().getDescription())
                .unityCost(item.getProduct().getUnityCost())
                .quantity(item.getQuantity())
                .build()
        ).collect(Collectors.toSet());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid  OrderInfoDTO dto){
        OrderInfo order = orderInfoService.save(dto);
        return order.getId();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @Valid @RequestBody OrderInfo order){
        orders.findById(id)
                .map( foundOrder -> {
                    order.setId(foundOrder.getId());
                    orders.save(order);
                    return foundOrder;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Update failed - Order not found"));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody @Valid UpdateOrderStatusDTO updateStatusDTO){
        orderInfoService.updateStatus(id, OrderStatus.valueOf(updateStatusDTO.getNewStatus()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Class<Void> delete(@PathVariable Integer id){
        Optional<OrderInfo> order = orders.findById(id);
        if(order.isPresent()){
            orders.delete(order.get());
            return Void.TYPE;
        }
        else throw new ResponseStatusException(NOT_FOUND, "Delete failed - Order not found");
    }

}
