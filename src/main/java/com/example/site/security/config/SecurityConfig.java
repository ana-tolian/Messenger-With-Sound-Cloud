package com.example.site.security.config;

import com.example.site.entity.User;
import com.example.site.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService (PasswordEncoder passwordEncoder) {
//        List<UserDetails> userDetailsList = new ArrayList<>();
//
//        userDetailsList.add(new User(
//                0, "Anatoliy", passwordEncoder.encode("password"), "The first user"));
//        userDetailsList.add(new User(
//                0, "Andrey", passwordEncoder.encode("password"), "The second user"));
//
//        return new InMemoryUserDetailsManager(userDetailsList);
//    }

//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository) {
//        return username -> {
//            User user = userRepository.findByUsername(username);
//            if (user != null) return user;
//            throw new UsernameNotFoundException("User ‘" + username + "’ not found");
//        };
//    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/", "/home", "/albums", "/load").hasRole("USER")
                .requestMatchers("/register", "/login", "/**").permitAll()
                .and()
                .build();
    }

}
