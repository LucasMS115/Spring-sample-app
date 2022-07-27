package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.Product;
import io.github.LucasMS115.spring_sales.domain.repository.Products;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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

        Example<Product> example = Example.of(filter, matcher);
        return products.findAll(example);
    }

    //to do: add validation to this request body list
    //https://stackoverflow.com/questions/28150405/validation-of-a-list-of-objects-in-spring
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> save(@RequestBody List<Product> newProducts){
        ArrayList<Product> createdProducts = new ArrayList<Product>();
        System.out.println(newProducts);

        newProducts.forEach(product -> {
            products.save(product);
            createdProducts.add(product);
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
    public void update(@PathVariable Integer id, @RequestBody @Valid Product product){
        products.findById(id)
                .map( foundProduct -> {
                    product.setId(foundProduct.getId());
                    products.save(product);
                    return foundProduct;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Update failed - Product not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Class<Void> delete(@PathVariable Integer id){
        Optional product = products.findById(id);
        if(product.isPresent()){
            products.delete((Product) product.get());
            return Void.TYPE;
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delete failed - Product not found");
    }

}
