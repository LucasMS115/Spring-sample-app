package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.OrderInfo;
import io.github.LucasMS115.spring_sales.domain.repository.OrderInfos;
import io.github.LucasMS115.spring_sales.domain.repository.Customers;
import io.github.LucasMS115.spring_sales.service.OrderInfoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


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

    @GetMapping("/{id}")
    public OrderInfo getById(@PathVariable("id") Integer id) {
        return orders
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
                );
    }

    @GetMapping("/find")
    public List<OrderInfo> find(OrderInfo filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);
        return orders.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderInfo save(@RequestBody OrderInfoService orderAndProduct){


        return null;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody OrderInfo order){
        orders.findById(id)
                .map( foundOrder -> {
                    order.setId(foundOrder.getId());
                    orders.save(order);
                    return foundOrder;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Update failed - Order not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Class<Void> delete(@PathVariable Integer id){
        Optional order = orders.findById(id);
        if(order.isPresent()){
            orders.delete((OrderInfo) order.get());
            return Void.TYPE;
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delete failed - Order not found");
    }

}
