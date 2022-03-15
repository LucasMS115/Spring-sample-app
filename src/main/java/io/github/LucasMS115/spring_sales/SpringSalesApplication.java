package io.github.LucasMS115.spring_sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

	public static void main(String[] args) {
		SpringApplication.run(SpringSalesApplication.class, args);
	}
}
