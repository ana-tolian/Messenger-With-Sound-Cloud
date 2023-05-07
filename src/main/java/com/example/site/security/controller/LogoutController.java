package com.example.site.security.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @RequestMapping("/logout")
    public String exit (){//HttpServletRequest request) {       //TODO
//        try {
//            request.logout();
//        } catch (ServletException e) {
//            throw new RuntimeException(e);
//        }
        return "redirect:/login";
    }


}
