package com.gcu.charactertracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/favorites")
public class FavoriteController {

    @GetMapping
    public String favoritesPage() {
        return "users/favorites";
    }
}