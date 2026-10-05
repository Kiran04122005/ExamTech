package com.examtech.controller;

import com.examtech.model.User;
import com.examtech.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ================= REGISTER =================

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user,
            Model model) {

        if (userService.userExists(user.getEmail())) {
            model.addAttribute(
                    "error",
                    "Email is already registered.");
            return "register";
        }

        user.setRole("CANDIDATE");
        userService.registerUser(user);

        model.addAttribute(
                "success",
                "Registration successful. You can now login.");

        return "register";
    }

    // ================= VIEW PROFILE =================

    @GetMapping("/student/profile")
    public String showProfile(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "profile";
    }

    // ================= EDIT PROFILE =================

    @GetMapping("/student/profile/edit")
    public String showEditProfile(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "edit-profile";
    }

    // ================= SAVE PROFILE =================

    @PostMapping("/student/profile/edit")
    public String updateProfile(
            @ModelAttribute User updatedUser,
            HttpSession session,
            Model model) {

        User currentUser = (User) session.getAttribute("loggedInUser");

        if (currentUser == null) {
            return "redirect:/login";
        }

        if (!"STUDENT".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/login";
        }

        // Check whether the new email belongs to another user
        User existingUser = userService.findUserByEmail(
                updatedUser.getEmail()).orElse(null);

        if (existingUser != null
                && !existingUser.getId()
                        .equals(currentUser.getId())) {

            model.addAttribute(
                    "error",
                    "This email is already registered by another account.");

            model.addAttribute("user", currentUser);

            return "edit-profile";
        }

        // Update only allowed profile fields
        currentUser.setName(updatedUser.getName());
        currentUser.setEmail(updatedUser.getEmail());

        User savedUser = userService.updateUser(currentUser);

        // Update the session with the latest information
        session.setAttribute("loggedInUser", savedUser);

        return "redirect:/student/profile";
    }
}