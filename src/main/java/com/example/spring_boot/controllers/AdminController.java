package com.example.spring_boot.controllers;
import com.example.spring_boot.model.Role;
import com.example.spring_boot.model.User;
import com.example.spring_boot.service.RoleService;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String allUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        List<Role> userRoles = roleService.getRoles();
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("user", new User());
        model.addAttribute("userAuth", userService.getUserByName(principal.getName()));
        return "admin";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "/admin";
    }

    @GetMapping("admin/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        return "redirect:/admin";
    }

    @PostMapping("admin/{id}/update")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("admin/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}


