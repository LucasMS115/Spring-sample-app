package io.github.LucasMS115.spring_sales;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import io.github.LucasMS115.spring_sales.domain.repository.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class SpringSalesApplication {

	@Autowired
	private String testBean;

	@Value("${spring.application.name}")
	private String appName;

	@GetMapping("/hello")
	public String helloWorld(){
		return testBean + " of the " + appName;
	}

	@Bean
	public CommandLineRunner init(@Autowired Customers customers){
		return args -> {
			customers.create(new Customer("Lucas M Sales"));
			customers.create(new Customer("Bojji"));

			List<Customer> allCustomers = customers.listAll();
			allCustomers.forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSalesApplication.class, args);
	}
}
