package com.example.site.security.controller;

import com.example.site.data.PlaylistRepository;
import com.example.site.data.UserRepository;
import com.example.site.entity.Playlist;
import com.example.site.entity.User;
import com.example.site.security.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, PlaylistRepository playlistRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping
    public String getRegisterForm () {
        return "register";
    }

    @PostMapping
    public String processRegistration (Model model, RegistrationForm registrationForm) {
        User user = userRepository.findByUsername(registrationForm.getUsername());

        if (user != null) {
            model.addAttribute("error", true);
            model.addAttribute("error_mes", "Пользователь уже существует");
            return "register";
        }

        if (!registrationForm.getPasswordAgain().equals(registrationForm.getPassword())) {
            model.addAttribute("error2", true);
            model.addAttribute("error_mes2", "Пароли не совпадают");
            return "register";
        }

        user = userRepository.save(registrationForm.toUser(passwordEncoder));
        playlistRepository.save(new Playlist("Основной", "", user));
        return "redirect:/login";
    }
}
