package com.example.site.security;

import com.example.site.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationForm {
    private final String username;
    private final String password;
    private final String passwordAgain;
    private final String description;

    public RegistrationForm(String username, String password, String passwordAgain, String description) {
        this.username = username;
        this.password = password;
        this.description = description;
        this.passwordAgain = passwordAgain;
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

    public String getPasswordAgain () {
        return passwordAgain;
    }

    public String getDescription() {
        return description;
    }
}
