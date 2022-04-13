package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.Product;
import io.github.LucasMS115.spring_sales.domain.repository.Products;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final Products products;

    public ProductController(Products products) {
        this.products = products;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello - Product";
    }

    @GetMapping()
    public List<Product> list(){
        return products.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") Integer id) {
        return products
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                );
    }

    @GetMapping("/find")
    public List<Product> find(Product filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);
        return products.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> save(@RequestBody Product[] newProducts){
        ArrayList<Product> createdProducts = new ArrayList<Product>();
        System.out.println(newProducts);
        Arrays.stream(newProducts).forEach(p -> {
            products.save(p);
            createdProducts.add(p);
        });
        return createdProducts;
    }

    /*
    Example post
    {
        "brand": "Rubik's",
        "name": "3x3 Rubik's cube",
        "description": "The original Rubik's cube :)",
        "unityCost": "50.00"
    }
     */

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Product product){
        products.findById(id)
                .map( foundProduct -> {
                    product.setId(foundProduct.getId());
                    products.save(product);
                    return foundProduct;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Update failed - Product not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        Optional product = products.findById(id);
        if(product.isPresent()) products.delete((Product) product.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delete failed - Product not found");
    }

}
