package com.example.site.web.controller;

import com.example.site.data.DialogRepository;
import com.example.site.data.UserRepository;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchEngineController {

    private final DialogRepository dialogRepository;
    private final UserRepository userRepository;

    @Autowired
    public SearchEngineController (DialogRepository dialogRepository, UserRepository userRepository) {
        this.dialogRepository = dialogRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dialog")
    public String findDialog (@RequestParam(value = "dl", required = true) String dialogName,
                              Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("lastMessages", dialogRepository.
                getDialogsForModel(dialogRepository.getDialogsByTitle(user, dialogName)));
        return "dialog";
    }

    @GetMapping("/contact")
    public String findContact (@RequestParam(value = "user", required = true) String contactName, Model model) {
        if (contactName.equals(""))
            return "redirect:/contacts";

        model.addAttribute("users", userRepository.findByUsernameStartsWith(contactName));

        return "contact-from-user";
    }
}
