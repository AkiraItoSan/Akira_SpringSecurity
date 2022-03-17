package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(value = "/list")
    public String printUsers(ModelMap model) {
        model.addAttribute(userService.listUsers());
        return "user";
    }

    @PostMapping()
    public String create(@RequestParam("name") String name,
                         @RequestParam("lastName") String lastName,
                         @RequestParam("email") String email) {
        userService.add(new User(name, lastName, email, true));
        return "redirect:/admin/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id) {

        userService.add(user);

        return "redirect:/admin/list";
    }

    @GetMapping("/{id}/delete")
    public String delete(@ModelAttribute("user") User user) {

        userService.remove(user);
        return "redirect:/admin/list";
    }
}
