package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.domain.entity.AppUser;
import io.github.LucasMS115.spring_sales.service.implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {
    private final UserServiceImplementation userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUser save(@RequestBody @Valid AppUser appUser) {
        String encryptedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encryptedPassword);
        return userService.save(appUser);
    }
}
