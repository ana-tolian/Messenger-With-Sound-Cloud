package com.example.site.control;

import com.example.site.entity.User;
import com.example.site.repository.UserRepository;
import com.example.site.security.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getLoginForm () {
        return "login";
    }

    @PostMapping
    public String auth(Model model, LoginForm loginForm) {  //TODO
        User user = userRepository.findByUsername(loginForm.getUsername());

        if (user != null) {
            if (user.getPassword().equals(passwordEncoder.encode(loginForm.getPassword())))
                return "register";
        } else
            model.addAttribute("message", "Неправильное имя пользователя или пароль");
        return "login";
    }
}
