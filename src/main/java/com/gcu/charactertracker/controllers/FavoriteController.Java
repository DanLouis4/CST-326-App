package com.gcu.charactertracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.services.CharacterService;
import com.gcu.charactertracker.services.FavoriteService;

@Controller
@RequestMapping("/users/favorites")
public class FavoriteController {



    @Autowired
    private CharacterService characterService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public String favoritesPage(Model model) {

        Integer userId = 1; // Placeholder user ID since UserEntity is not implemented
        model.addAttribute("favorites", favoriteService.getFavorites(userId));
        return "users/favorites";
    }

    @PostMapping("/add/{characterId}")
    public String addFavorite(@PathVariable Integer characterId) {
       
        Integer userId = 1; // Placeholder user ID since UserEntity is not implemented
        favoriteService.addFavorite(userId, characterId);
        
        return "redirect:/characters";
    }

    @PostMapping("/remove/{characterId}")
    public String removeFavorite(@PathVariable Integer characterId)
    {
        Integer userId = 1; // Placeholder user ID since UserEntity is not implemented
        favoriteService.removeFavorite(userId, characterId);
        
        return "redirect:/characters";
    }
    
}