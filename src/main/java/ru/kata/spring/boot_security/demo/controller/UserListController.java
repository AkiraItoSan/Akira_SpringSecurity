package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class UserListController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roles;

    @GetMapping(value = "/list")
    public String printUsers(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, ModelMap model) {
        model.addAttribute(userService.listUsers());
        model.addAttribute("authUser", userService.findByEmail(authUser.getUsername()).orElseThrow());
        model.addAttribute("newuser", new User());

        return "admin-panel";
    }

    @PostMapping()
    public String create(@ModelAttribute("newuser") User user) {
        System.out.println("create : " + user.toString());
        userService.add(user);
        return "redirect:/admin/list";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("editUser") User user,
                         @PathVariable("id") Long id) {

        System.out.println("update : " + user.toString());
        userService.add(user);

        return "redirect:/admin/list";
    }

    @GetMapping("/{id}/delete")
    public String delete(@ModelAttribute("user") User user, Model model) {

        userService.remove(user);
        return "redirect:/admin/list";
    }
}
