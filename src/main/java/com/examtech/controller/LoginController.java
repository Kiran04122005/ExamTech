package com.examtech.controller;

import com.examtech.model.User;
import com.examtech.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Process login
    @PostMapping("/login")
    public String loginUser(
            @RequestParam("role") String selectedRole,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {

        // Find user by email
        User user = userService.findUserByEmail(email).orElse(null);

        // User does not exist
        if (user == null) {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }

        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }

        // Check selected login role against database role
        if (!selectedRole.equalsIgnoreCase(user.getRole())) {
            model.addAttribute(
                    "error",
                    "The selected login type does not match this account.");
            return "login";
        }

        // Store logged-in user in session
        session.setAttribute("loggedInUser", user);

        // Redirect according to role
        if ("EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/examiner/dashboard";
        }

        if ("STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/student/dashboard";
        }

        // Unknown role
        session.invalidate();
        model.addAttribute("error", "Invalid account role.");
        return "login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}