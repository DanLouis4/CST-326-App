package com.gcu.charactertracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

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

    @Autowired
    private CharacterRepository characterRepository;

    @GetMapping
    public String favoritesPage(Model model) {

        Integer userId = 1; // Placeholder user ID since UserEntity is not implemented

        List<Favorite> favorites =
                favoriteService.getFavorites(userId);

        List<Integer> ids = favorites.stream()
                .map(Favorite::getCharacterId)
                .toList();

        List<CharacterEntity> characters =
                characterRepository.findByCharacterIdIn(ids);

        model.addAttribute("characters", characters);

        return "users/favorites";
    }

    @PostMapping("/add/{characterId}")
    public void addFavorite(Integer userId, Integer characterId)
{
    if (favoriteRepository
            .findByUserIdAndCharacterId(userId, characterId)
            .isEmpty())
    {
        Favorite favorite = new Favorite();

        favorite.setUserId(userId);
        favorite.setCharacterId(characterId);

        favoriteRepository.save(favorite);
    }
}

    @PostMapping("/remove/{characterId}")
    public void removeFavorite(Integer userId, Integer characterId)
{
    favoriteRepository
            .findByUserIdAndCharacterId(userId, characterId)
            .ifPresent(favoriteRepository::delete);
}