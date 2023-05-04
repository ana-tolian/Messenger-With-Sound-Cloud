package com.example.site.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig (UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

//    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                    .requestMatchers("/", "/home", "/albums", "/load", "/profile").hasRole("USER")
                    .requestMatchers("/register", "/login", "/**").permitAll()

                .and()
                    .formLogin()
                    .loginPage("/login")
                    .successForwardUrl("/profile")

                .and()
                    .logout()
                    .logoutSuccessUrl("/")

                .and()
                    .build();
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
//


}
