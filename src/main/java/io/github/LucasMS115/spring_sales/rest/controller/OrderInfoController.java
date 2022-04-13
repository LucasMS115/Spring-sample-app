package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.OrderInfo;
import io.github.LucasMS115.spring_sales.domain.repository.OrderInfos;
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

    public OrderInfoController(OrderInfos orders) {
        this.orders = orders;
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
    public OrderInfo save(@RequestBody OrderInfo order){
        return orders.save(order);
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
    public void delete(@PathVariable Integer id){
        Optional order = orders.findById(id);
        if(order.isPresent()) orders.delete((OrderInfo) order.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delete failed - Order not found");
    }

}
