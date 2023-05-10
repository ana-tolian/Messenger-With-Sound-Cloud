package com.example.site.data;

import com.example.site.entity.Contact;
import com.example.site.entity.User;

import java.util.List;


public interface ContactRepository {

    List<Contact> getUserContacts(User user);
    Contact saveContact(Contact contact);
}
