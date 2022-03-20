package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
    public String printUsers(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,ModelMap model) {
        model.addAttribute(userService.listUsers());
        model.addAttribute("authUser", userService.findByEmail(authUser.getUsername()).orElseThrow());
        model.addAttribute("newuser", new User());
        model.addAttribute("roles", roles.findAll());
        return "user";
    }

    @PostMapping()
    public String create(@ModelAttribute("newuser") User user) {
        System.out.println(user.toString());

//        System.out.println(string);
        userService.add(user);
        return "redirect:/admin/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,
                       Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("authUser", userService.findByEmail(authUser.getUsername()).orElseThrow());

        return "edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id) {
        System.out.println(user.toString());
        userService.add(user);

        return "redirect:/admin/list";
    }

    @GetMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,
                         @ModelAttribute("user") User user, Model model) {
        model.addAttribute("authUser", userService.findByEmail(authUser.getUsername()).orElseThrow());

        userService.remove(user);
        return "redirect:/admin/list";
    }
}
