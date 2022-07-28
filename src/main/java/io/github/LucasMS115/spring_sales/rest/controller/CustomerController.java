package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import io.github.LucasMS115.spring_sales.domain.repository.Customers;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@Api
public class CustomerController {

    private final Customers customers;

    public CustomerController(Customers customers) {
        this.customers = customers;
    }

    @GetMapping(value = {"/hello/{name}", "/hello"})
    @ApiOperation("Just testing if the api is up :)")
    public String helloCustomer(@PathVariable(name = "name", required = false) String name){
        if(name != null) return String.format("Hello %s", name);
        return "Hello customer!";
    }


    @GetMapping("/{id}")
    @ApiOperation("Search for a customer with a given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Found It!"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    public Customer getCustomerById(@PathVariable("id") @ApiParam("Customer ID") Integer id){
        return customers
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
                );
    }

    @GetMapping("/{id}/orders")
    public Customer getCustomerWithOrdersById(@PathVariable("id") Integer id) {
        try {
            return customers.findCustomerFetchOrderInfo(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found \n" + e + "\n \"Customer not found");
        }
    }

    @GetMapping("/list")
    public List<Customer> getCustomers(){
        return customers.findAll();
    }

    @GetMapping("/list/{name}")
    public List<Customer> getCustomersByName(@PathVariable String name){
        return  customers.customFindByNameLike(name);
    }

    @GetMapping("/find")
    public List<Customer> find(Customer filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Customer> example = Example.of(filter, matcher);
        return customers.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save new customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customer created!"),
            @ApiResponse(code = 404, message = "Validation error :(")
    })
    public Customer save(@RequestBody @Valid Customer customer){
        return customers.save(customer);
    }

    //the put mapping will set null to every field that isn't specified on the request body
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Customer customer){
        customers.findById(id)
                .map( foundCustomer -> {
                    customer.setId(foundCustomer.getId());
                    customers.save(customer);
                    return foundCustomer;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Delete failed - Customer not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Class<Void> delete(@PathVariable Integer id){
        Optional<Customer> customer = customers.findById(id);
        if(customer.isPresent()){
            customers.delete((Customer) customer.get());
            return Void.TYPE;
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delete failed - Customer not found");

    }


/*  ### Controller - ResponseEntity version ###
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

*/

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
