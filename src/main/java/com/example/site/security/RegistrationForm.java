package com.example.site.security;

import com.example.site.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationForm {
    private final String username;
    private final String password;
    private final String description;

    public RegistrationForm(String username, String password, String description) {
        this.username = username;
        this.password = password;
        this.description = description;
    }

    public User toUser (PasswordEncoder passwordEncoder) {
        return new User(0, username, passwordEncoder.encode(password), description, "");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }
}
