package ua.foxminded.scheduleapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/panel";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_STUDENT"))) {
            return "redirect:/student/schedule";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_TEACHER"))) {
            return "redirect:/teacher/schedule";
        }
        
        return "redirect:/";
    }
}
