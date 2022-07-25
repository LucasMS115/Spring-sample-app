package io.github.LucasMS115.spring_sales;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.context.annotation.Configuration
@DevProfile
public class Configuration {

    @Bean(name = "testBean")
    public String testBean(){
        return "This bean is a test";
    }

    @Bean
    public CommandLineRunner execute(){
        return args -> {
          System.out.println("\nRUNNING WITH DEV CONFIG");
        };
    }
}

