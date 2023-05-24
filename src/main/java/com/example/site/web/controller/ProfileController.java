package com.example.site.web.controller;

import com.example.site.data.ContactRepository;
import com.example.site.data.DialogRepository;
import com.example.site.data.UserRepository;
import com.example.site.entity.Dialog;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final DialogRepository dialogRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public  ProfileController (DialogRepository dialogRepository,
                               ContactRepository contactRepository,
                               UserRepository userRepository) {
        this.dialogRepository = dialogRepository;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dialogs")
    public String getDialogs (Model model, Authentication authentication) {
        getUserPage(model, authentication);
        return "dialog";
    }

    @PostMapping("/dialogs/create")
    @ResponseBody
    public String createDialog (@RequestParam(value = "user", required = true) String username,
                                    Authentication authentication) {
        User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
        int id = dialogRepository.saveDialog(new Dialog(username, user, userRepository.findByUsername(username)));
        return "" + id;
    }

    @GetMapping("/show")
    public String getUserProfile (@RequestParam(value = "user", required = true) Integer id, Model model) {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);

        return "profile-pane";
    }

    @GetMapping
    public String get (Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            getUserPage(model, authentication);
            return "profile";
        } else
            return "redirect:/login";
    }

    @PostMapping
    public String post (Model model, Authentication authentication) {
        getUserPage(model, authentication);
        return "profile";
    }

    private void getUserPage (Model model, Authentication authentication) {
        User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
        model.addAttribute("user", user);

        model.addAttribute("lastMessages", dialogRepository.getDialogsForModel(dialogRepository.getDialogs(user)));
        model.addAttribute("Contacts", contactRepository.getUserContacts(user));
    }
}
