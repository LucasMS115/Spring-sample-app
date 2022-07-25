package io.github.LucasMS115.spring_sales.service.implementation;

import io.github.LucasMS115.spring_sales.domain.entity.AppUser;
import io.github.LucasMS115.spring_sales.domain.repository.AppUsers;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserDetailsService {

    private final AppUsers usersRepository;

    public UserServiceImplementation(AppUsers usersRepository) {
        this.usersRepository = usersRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if(!username.equals("Jhon Do")) {
//            throw new UsernameNotFoundException(String.format("Username <%s> not found", username));
//        }
//        return User.builder()
//                .username("Jhon Do")
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

    public AppUser save(AppUser appUser) {
        return usersRepository.save(appUser);
    }
}
