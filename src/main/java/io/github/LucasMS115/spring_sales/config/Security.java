package io.github.LucasMS115.spring_sales.config;

import io.github.LucasMS115.spring_sales.service.implementation.UserServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    private final UserServiceImplementation userService;

    public Security(UserServiceImplementation userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //Create a custom encoder
        /* PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override
            //will encrypt the password
            public String encode(CharSequence rawPassword) {
                return null;
            }

            @Override
            //will check if the passed string matches the encrypted version of the password
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return false;
            }
        }*/

        return new BCryptPasswordEncoder();
    }

    @Override
    // will authenticate the users - defines where the user info comes from
    // in memory version
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("Jhon Do")
//                .password(passwordEncoder().encode("a1b2c3d4"))
//                .roles("DEV", "ADMIN");
//    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    // will define which stuff the user has access to
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/customers*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/customers/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/products*").authenticated()
                .antMatchers("/api/products/*").authenticated()
                .antMatchers("/api/orders*").hasAnyRole("ADMIN")
                .antMatchers("/api/orders/*").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/users").permitAll()
                .anyRequest().permitAll()
        .and() //every method call returns something, use and() to return to the start (http in this case)
            //.formLogin(); // it is possible to create a custom form and pass the path for it here (it has to match some conditions to work)
            .httpBasic();

    }
}
