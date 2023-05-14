package com.example.site.data;

import com.example.site.entity.User;

import java.util.List;


public interface UserRepository extends Repository<User> {

    User findByUsername(String username);
    List<User> findByUsernameStartsWith(String username);
    List<User> findByUsernameStartsWithFromContacts(String contactname);
    User findById(int id);
}
