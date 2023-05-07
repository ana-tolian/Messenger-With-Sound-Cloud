package com.example.site.data;

import com.example.site.entity.User;


public interface UserRepository extends Repository<User> {

    User findByUsername(String username);
    User findById(int id);
}
