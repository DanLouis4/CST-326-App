package com.gcu.charactertracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.charactertracker.services.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    @SuppressWarnings("CallToPrintStackTrace")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String role,
            Model model) {

        System.out.println("REGISTER FORM SUBMITTED");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "auth/register";
        }

        try {
            userService.registerUser(username, email, password, role);
            System.out.println("USER REGISTERED SUCCESSFULLY");
            return "redirect:/auth/login?registered=true";

        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "Registration failed: " + ex.getMessage());
            return "auth/register";
        }
    }
};