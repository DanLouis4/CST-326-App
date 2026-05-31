package com.gcu.charactertracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(
            SearchService searchService) {

        this.searchService = searchService;
    }

    @GetMapping
    public String searchCharacters(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String race,
            @RequestParam(required = false)
            String characterClass,
            Model model) {

        model.addAttribute(
                "characters",
                searchService.search(
                        keyword,
                        race,
                        characterClass));

        return "search/search";
    }
}