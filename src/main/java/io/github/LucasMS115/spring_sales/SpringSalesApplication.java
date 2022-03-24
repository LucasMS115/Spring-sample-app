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
import java.util.Optional;

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

//	**** JpaRepository VERSION ****
	@Bean
	public CommandLineRunner init(@Autowired Customers customers) {
		return args -> {
			customers.save(new Customer("Lucas MS"));
			customers.save(new Customer("Bojji"));
			customers.save(new Customer("King Bojji 1"));
			customers.save(new Customer("Kage"));
			customers.save(new Customer("Miranjo"));

			System.out.println(customers.existsById(1));
			Customer me = customers.findById(1).get();
			me.setName("Lucas M. Sales");
			customers.save(me);

			System.out.println("\n### search by name:");
			List<Customer> customersByName = customers.findByNameLike("Bojji"); // the "like" didn't well
			customersByName.forEach(System.out::println);

			System.out.println("\n## get by name");
			System.out.println(customers.getByName("Miranjo"));
			System.out.println("## get by id");
			System.out.println(customers.findById(3).get());

			System.out.println("\n## delete by obj");
			customers.delete(customers.getByName("Miranjo"));
			System.out.println("## delete by id");
			customers.deleteById(3);

			System.out.println("\n### List all:");
			List<Customer> allCustomers = customers.findAll();
			allCustomers.forEach(System.out::println);
		};
	}



//		**** JPA VERSION ****
//		@Bean
//		public CommandLineRunner init(@Autowired Customers customers){
//			return args -> {
//				Customer teste = new Customer("Lucas MS");
//				customers.create(teste);
//				customers.create(new Customer("Bojji"));
//				customers.create(new Customer("King Bojji 1"));
//				customers.create(new Customer("Kage"));
//				customers.create(new Customer("Miranjo"));
//
//				Customer me = customers.getByName("Lucas");
//				me.setName("Lucas M. Sales");
//				customers.update(me);
//
//				System.out.println("\n### By name:");
//				List<Customer> customersByName = customers.listByName("Bojji");
//				customersByName.forEach(System.out::println);
//
//				System.out.println("\n## search by name");
//				System.out.println(customers.getByName("Miranjo"));
//				System.out.println("## search by id");
//				System.out.println(customers.getById(3));
//
//				System.out.println("\n## delete by obj");
//				customers.delete(customers.getByName("Miranjo"));
//				System.out.println("## delete by id");
//				customers.delete(3);
//
//				System.out.println("\n### List all:");
//				List<Customer> allCustomers = customers.listAll();
//				allCustomers.forEach(System.out::println);
//			};
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSalesApplication.class, args);
	}
}