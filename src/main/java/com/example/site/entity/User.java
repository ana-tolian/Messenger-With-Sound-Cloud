package com.example.site.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class User implements UserDetails {

    private final int id;
    private final String username;
    private final String password;
    private final String description;
    private final String imgHref;

    public User (String username, String password, String description, String imgHref) {
        this(0, username, password, description, imgHref);
    }

    public User (int id, String username, String password, String description, String imgHref) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.description = description;
        this.imgHref = imgHref;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImgHref() {
        return imgHref;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(description, user.description) && Objects.equals(imgHref, user.imgHref);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", imgHref='" + imgHref + '\'' +
                '}';
    }
}
