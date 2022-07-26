package io.github.LucasMS115.spring_sales.service.implementation;

import io.github.LucasMS115.spring_sales.domain.entity.AppUser;
import io.github.LucasMS115.spring_sales.domain.repository.AppUsers;
import io.github.LucasMS115.spring_sales.exception.InvalidPasswordException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImplementation implements UserDetailsService {

    private final AppUsers usersRepository;

    public UserServiceImplementation(AppUsers usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public AppUser save(AppUser appUser) {
        return usersRepository.save(appUser);
    }

    public UserDetails authenticate(AppUser appUser) {
        UserDetails userDetails = loadUserByUsername(appUser.getUsername());
        if(new BCryptPasswordEncoder().matches(appUser.getPassword(), userDetails.getPassword())) {
            return userDetails;
        }

        throw new InvalidPasswordException();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if(!username.equals("Jhon Doe")) {
//            throw new UsernameNotFoundException(String.format("Username <%s> not found", username));
//        }
//        return User.builder()
//                .username("Jhon Doe")
//                .password(new BCryptPasswordEncoder().encode("a1b2c3d4"))
//                .roles("ADMIN", "DEV")
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = usersRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username <%s> not found", username)));

        String[] roles = appUser.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};

        return User
                .builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .build();
    }
}
