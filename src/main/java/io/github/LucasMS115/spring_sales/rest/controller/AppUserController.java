package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.AppUser;
import io.github.LucasMS115.spring_sales.exception.InvalidPasswordException;
import io.github.LucasMS115.spring_sales.rest.dto.CredentialsDTO;
import io.github.LucasMS115.spring_sales.rest.dto.TokenDTO;
import io.github.LucasMS115.spring_sales.security.jwt.JwtService;
import io.github.LucasMS115.spring_sales.service.implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {
    private final UserServiceImplementation userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUser save(@RequestBody @Valid AppUser appUser) {
        String encryptedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encryptedPassword);
        return userService.save(appUser);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentials) {
        try {
            AppUser appUser = AppUser.builder()
                    .username(credentials.getUsername())
                    .password(credentials.getPassword())
                    .build();

            UserDetails authenticatedUser = userService.authenticate(appUser);
            String token = jwtService.generateToken(appUser);

            return new TokenDTO(appUser.getUsername(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
