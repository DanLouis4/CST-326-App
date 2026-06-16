package com.gcu.charactertracker.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.entities.FavoriteEntity;
import com.gcu.charactertracker.services.CharacterAccessService;
import com.gcu.charactertracker.services.FavoriteService;
import com.gcu.charactertracker.services.SearchService;


@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;
    private final CharacterAccessService characterAccessService;
    private final FavoriteService favoriteService;


    public SearchController(
            SearchService searchService,
            CharacterAccessService characterAccessService,
            FavoriteService favoriteService) {

        this.searchService = searchService;
        this.characterAccessService = characterAccessService;
        this.favoriteService = favoriteService;
    }

    /**
     * Handles GET requests to /search. Performs a search based on the provided parameters and displays the results.
      * The search results are filtered based on the user's authentication status and permissions:
     * @param keyword The keyword to search for.
     * @param race The race to filter by.
     * @param characterClass The character class to filter by.
     * @param classType The class type to filter by.
     * @param model The model to add attributes to.
     * @param authentication The authentication object representing the current user.
     * @return the name of the view to render, which is "search/search". The model will contain the search results and the original search parameters for display in the view.
     */

    @GetMapping
    public String searchCharacters(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String race,
            @RequestParam(required = false) String characterClass,
            @RequestParam(required = false) String classType,
            Model model,
            Authentication authentication) {

        List<CharacterEntity> searchResults = searchService.search(
                keyword,
                race,
                characterClass,
                classType);

        List<CharacterEntity> visibleResults = searchResults
                .stream()
                .filter(character -> characterAccessService.canViewCharacter(character, authentication))
                .toList();

        model.addAttribute("characters", visibleResults);

        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedRace", race);
        model.addAttribute("selectedClass", characterClass);
        model.addAttribute("selectedClassType", classType);

        Integer userId = characterAccessService.getLoggedInUserId(authentication);
        List<Integer> favoriteCharacterIds = favoriteService.getFavorites(userId)
                .stream()
                .map(FavoriteEntity::getCharacterId)
                .toList();
        model.addAttribute("favoriteCharacterIds", favoriteCharacterIds);

        return "search/search";
    }
}