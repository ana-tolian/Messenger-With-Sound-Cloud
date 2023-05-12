package com.example.site.web.controller;

import com.example.site.data.ContactRepository;
import com.example.site.data.UserRepository;
import com.example.site.entity.Contact;
import com.example.site.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactController (ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getContactsPage (Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated())
            return "redirect:login";

        User user = (User) authentication.getPrincipal();

        model.addAttribute("Contacts", contactRepository.getUserContacts(user));
        model.addAttribute("username", user.getUsername());
        model.addAttribute("imgHref", user.getImgHref());

        return "contacts";
    }

    @PostMapping
    public String saveContact (@RequestParam(value = "user", required = true) String username,
                               @RequestParam(value = "status", required = true) String status,
                               Authentication authentication, Model model) {
        User referer = userRepository.findByUsername(username);
        User owner = (User) authentication.getPrincipal();
        Contact contact = new Contact(owner, referer, status);

        contactRepository.saveContact(contact);
        model.addAttribute("contact", contact);

        return "contact";
    }
}
