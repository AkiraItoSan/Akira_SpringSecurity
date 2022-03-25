package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class UserListController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value = "/list")
    public String printUsers(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, ModelMap model) {
//        model.addAttribute(userService.listUsers());
        model.addAttribute("authUser", userService.findByEmail(authUser.getUsername()).orElseThrow());
//        model.addAttribute("newUser", new User());
        return "admin-panel";
    }

//    @PostMapping()
//    public String create(@ModelAttribute("newUser") User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.add(user);
//        return "redirect:/admin/list";
//    }

//    @PostMapping("/{id}")
//    public String update(@ModelAttribute("editUser") User user,
//                         @PathVariable("id") Long id) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.add(user);
//
//        return "redirect:/admin/list";
//    }
//
//    @GetMapping("/{id}/delete")
//    public String delete(@ModelAttribute("user") User user, Model model) {
//        userService.remove(user);
//        return "redirect:/admin/list";
//    }
}
