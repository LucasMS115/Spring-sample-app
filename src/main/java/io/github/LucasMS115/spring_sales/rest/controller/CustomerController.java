package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import io.github.LucasMS115.spring_sales.domain.repository.Customers;
import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public String helloCustomer(@PathVariable(name = "name", required = false) String name){
        if(name != null) return String.format("Hello %s", name);
        return "Hello customer!";
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

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity getCustomers(){
        //ResponseEntity is an object that represents the response body
        List<Customer> allCustomers = customers.findAll();
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/list/{name}")
    @ResponseBody
    public ResponseEntity getCustomersByName(@PathVariable String name){
        //ResponseEntity is an object that represents the response body
        List<Customer> foundCustomers = customers.customFindByNameLike(name);

        return ResponseEntity.ok(foundCustomers);
    }

    @GetMapping("/find")
    public ResponseEntity find(Customer filter){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);
        List<Customer> foundCustomers = customers.findAll(example);
        return ResponseEntity.ok(foundCustomers);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity save(@RequestBody Customer customer){
        Customer persistedCustomer = customers.save(customer); //apparently save do return the saved obj
        return ResponseEntity.ok(persistedCustomer);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Customer customer){
        return customers.findById(id)
                .map( foundCustomer -> {
                    customer.setId(foundCustomer.getId()); //add the id to the received customer
                    customers.save(customer);
                    return  ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id){
        //ResponseEntity is an object that represents the response body
        Optional<Customer> customer = customers.findById(id);
        if(!customer.isPresent()){
            return ResponseEntity.notFound().build();
        }

        customers.delete(customer.get());
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping("/{name}")
//    @ResponseBody
//    public ResponseEntity delete(@PathVariable String name){
//        //ResponseEntity is an object that represents the response body
//        Optional<Customer> customer = Optional.ofNullable(customers.getByName(name));
//        if(!customer.isPresent()){
//            return ResponseEntity.notFound().build();
//        }
//
//        customers.delete(customer.get());
//        return ResponseEntity.noContent().build();
//    }


}
