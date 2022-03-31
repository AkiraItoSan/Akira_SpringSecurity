package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.services.UserService;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String welcomePage(@AuthenticationPrincipal User authUser, Model model) {
        model.addAttribute("authUser", userService.findByEmail(authUser.getUsername()).orElseThrow());
        return "index";
    }
}