package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import io.github.LucasMS115.spring_sales.domain.repository.Customers;
import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private Customers customers;

    //@RequestMapping(
            //value = {"/hello/{name}", "/hello"}, //array of strings
            //method = RequestMethod.GET
            //,consumes = {"application/json", "application/xml"} //supported mime types for request body (useless here at a get)
            //,produces = {"application/json", "application/xml"} //possible return mime types, the client will choose
    //)
    @GetMapping(value = {"/hello/{name}", "/hello"}) //used to map get request, dnt need to use the one above
    @ResponseBody //without this one spring will think the url is going to return a web page
    public String helloCustomer(@PathVariable("name") String name){
        return String.format("Hello %s", name);
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getCustomerById(@PathVariable("id") Integer id){
        //ResponseEntity is an object that represents the response body
        Optional<Customer> customer = customers.findById(id);
        if(customer.isPresent()){
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.notFound().build();
    }


}
