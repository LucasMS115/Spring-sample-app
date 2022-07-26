package io.github.LucasMS115.spring_sales.config;

import io.github.LucasMS115.spring_sales.security.jwt.JwtAuthFilter;
import io.github.LucasMS115.spring_sales.security.jwt.JwtService;
import io.github.LucasMS115.spring_sales.service.implementation.UserServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    private final UserServiceImplementation userService;
    private final JwtService jwtService;

    public Security(UserServiceImplementation userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
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

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, userService);
    }

    @Override
    // will authenticate the users - defines where the user info comes from
    // in memory version
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("Jhon Doe")
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
                .antMatchers(HttpMethod.POST,"/api/users/auth").permitAll()
                .anyRequest().authenticated()

        .and() //every method call returns something, use and() to return to the start (http in this case)

            //.formLogin(); // it is possible to create a custom form and pass the path for it here (it has to match some conditions to work)
            //.httpBasic(); // this one is not that safe since we always send the user password through the requests

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // the app doesn't have a session, every request should have the auth token
        .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
