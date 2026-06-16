package com.gcu.charactertracker.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.entities.FavoriteEntity;
import com.gcu.charactertracker.services.CharacterAccessService;
import com.gcu.charactertracker.services.CharacterService;
import com.gcu.charactertracker.services.FavoriteService;

@Controller
@RequestMapping("/users/favorites")
@PreAuthorize("isAuthenticated()")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final CharacterService characterService;
    private final CharacterAccessService characterAccessService;

    public FavoriteController(
            FavoriteService favoriteService,
            CharacterService characterService,
            CharacterAccessService characterAccessService) {

        this.favoriteService = favoriteService;
        this.characterService = characterService;
        this.characterAccessService = characterAccessService;
    }

    @GetMapping
    public String favoritesPage(
            Model model,
            Authentication authentication) {

        Integer userId = characterAccessService.getLoggedInUserId(authentication);

        List<FavoriteEntity> favorites = favoriteService.getFavorites(userId);

        List<Integer> characterIds = favorites.stream()
                .map(FavoriteEntity::getCharacterId)
                .toList();

        List<CharacterEntity> characters = characterService.getCharactersByIds(characterIds);

        model.addAttribute("characters", characters);
        model.addAttribute("favoriteCharacterIds", characterIds);

        return "users/favorites";
    }

    @PostMapping("/add/{characterId}")
    public String addFavorite(
            @PathVariable Integer characterId,
            Authentication authentication,
            @RequestHeader(value = "Referer", required = false) String referer) {

        Integer userId = characterAccessService.getLoggedInUserId(authentication);

        favoriteService.addFavorite(userId, characterId);

        return redirectBack(referer);
    }

    @PostMapping("/remove/{characterId}")
    public String removeFavorite(
            @PathVariable Integer characterId,
            Authentication authentication,
            @RequestHeader(value = "Referer", required = false) String referer) {

        Integer userId = characterAccessService.getLoggedInUserId(authentication);

        favoriteService.removeFavorite(userId, characterId);

        return redirectBack(referer);
    }

    @PostMapping("/toggle/{characterId}")
    public Object toggleFavorite(
            @PathVariable Integer characterId,
            Authentication authentication,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
            @RequestHeader(value = "Referer", required = false) String referer) {

        Integer userId = characterAccessService.getLoggedInUserId(authentication);

        boolean favorited = favoriteService.toggleFavorite(userId, characterId);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return ResponseEntity.ok(Map.of("favorited", favorited));
        }

        return redirectBack(referer);
    }

    private String redirectBack(String referer) {
        if (referer == null || referer.isBlank()) {
            return "redirect:/characters";
        }

        return "redirect:" + referer;
    }
    
}