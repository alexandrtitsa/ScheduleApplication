package ua.foxminded.scheduleapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.scheduleapp.model.User;
import ua.foxminded.scheduleapp.service.UserService;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/panel")
    public String showAdminPanel(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/panel";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/panel";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/panel";
    }
}
