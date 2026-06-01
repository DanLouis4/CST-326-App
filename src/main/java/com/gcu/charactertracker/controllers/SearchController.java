package com.gcu.charactertracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.charactertracker.services.ClassService;
import com.gcu.charactertracker.services.RaceService;
import com.gcu.charactertracker.services.SearchService;


@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;
    private final ClassService classService;
    private final RaceService raceService;

    public SearchController(
            SearchService searchService,
            ClassService classService,
            RaceService raceService) {

        this.searchService = searchService;
        this.classService = classService;
        this.raceService = raceService;
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

        model.addAttribute("races", raceService.getAllRaces());
        model.addAttribute("characterClasses", classService.getAllClasses());
        model.addAttribute("classTypes", classService.getDistinctClassTypes());
        
        return "search/search";
    }
}