package com.example.site.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;


@Configuration
public class SecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig (UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean(name = "multipartresolver")
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        return multipartResolver;
    }
//        return new MultipartResolver() {
//            @Override
//            public boolean isMultipart(HttpServletRequest request) {
//                System.out.println("========================================== Multipart true");
//                System.out.println("========================================== ===============");
//                Iterator<String> iterator = request.getHeaderNames().asIterator();
//                for (Iterator<String> it = iterator; it.hasNext(); ) {
//                    String s = it.next();
//                    System.out.println(s + " : " + request.getHeader(s));
//
//                }
//
//                return  request.isUserInRole("USER") &&
//                        request.getMethod().equals("POST") &&
//                        request.getHeader("Content-Type") != null &&
//                        request.getHeader("Content-Type").contains("multipart");
//            }
//
//            @Override
//            public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
//                MultipartRequest multipartRequest = (MultipartRequest) request;
//                System.out.println("********Multipart");
//                return (MultipartHttpServletRequest) request;
//            }

//            @Override
//            public void cleanupMultipart(MultipartHttpServletRequest request) {
////                request.
//                System.out.println("Multipart clean");
//            }
//        };
//    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                    .requestMatchers("/debug").hasRole("ADMIN")
                    .requestMatchers("/upload", "/uploads/*").permitAll()
                    .requestMatchers("/", "/home", "/albums", "/load", "/uploads/*", "/profile/*",
                            "/creation", "/messages/*", "/contacts/*", "/search/*").authenticated()
                    .requestMatchers("/register", "/login", "/**").permitAll()
                    .anyRequest().authenticated()

                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/profile")
                    .successForwardUrl("/profile")

                .and()
                    .logout()
                    .logoutSuccessUrl("/login")

                .and()
                    .build();
    }

    @Override
    public void init (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }



//    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
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
