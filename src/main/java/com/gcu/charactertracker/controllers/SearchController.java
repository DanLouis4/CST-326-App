package com.gcu.charactertracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.charactertracker.services.SearchService;


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
            @RequestParam(required = false) String characterClass,
            @RequestParam(required = false) String classType,Model model) 
            {

                model.addAttribute(
                        "characters",
                        searchService.search(
                                keyword,
                                race,
                                characterClass,
                                classType));

                model.addAttribute("keyword", keyword);
                model.addAttribute("selectedRace", race);
                model.addAttribute("selectedClass", characterClass);
                model.addAttribute("selectedClassType", classType);
                
                
                return "search/search";
            }
}