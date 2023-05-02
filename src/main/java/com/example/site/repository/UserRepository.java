package com.example.site.repository;

import com.example.site.entity.User;


public interface UserRepository extends Repository<User> {

    User findByUsername(String username);
}
