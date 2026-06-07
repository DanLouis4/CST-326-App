/**
 * Controller responsible for handling
 * character-related web requests.
 * 
 * Provides CRUD operations for characters.
 * 
 * @author Daniel Louis
 * @version 1.0
 */
package com.gcu.charactertracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.entities.UserEntity;
import com.gcu.charactertracker.services.CharacterService;
import com.gcu.charactertracker.services.ClassService;
import com.gcu.charactertracker.services.RaceService;
import com.gcu.charactertracker.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private UserService userService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private ClassService classService;

    @GetMapping
    public String showCharacters(Model model) {
        model.addAttribute("characters", characterService.getAllCharacters());
        return "characters/characters";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("character", new CharacterEntity());
        model.addAttribute("races", raceService.getAllRaces());
        model.addAttribute("characterClasses", classService.getAllClasses());

        return "characters/character-create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createCharacter(
            @Valid @ModelAttribute("character") CharacterEntity character,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("races", raceService.getAllRaces());
            model.addAttribute("characterClasses", classService.getAllClasses());
            return "characters/character-create";
        }

        String username = authentication.getName();

        UserEntity loggedInUser = userService.getUserByUsername(username);

        character.setFlagged(false);
        character.setUserId(loggedInUser.getUserId());

        characterService.saveCharacter(character);

        redirectAttributes.addFlashAttribute("successMessage", "Character created successfully.");

        return "redirect:/characters/detail/" + character.getCharacterId();
    }

    @GetMapping("/detail/{id}")
    public String showCharacterDetail(@PathVariable Integer id, Model model) {
        CharacterEntity character = characterService.getCharacterById(id);

        if (character == null) {
            return "redirect:/characters";
        }

        model.addAttribute("character", character);
        return "characters/character-detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        CharacterEntity character = characterService.getCharacterById(id);

        if (character == null) {
            return "redirect:/characters";
        }

        model.addAttribute("character", character);
        model.addAttribute("races", raceService.getAllRaces());
        model.addAttribute("characterClasses", classService.getAllClasses());

        return "characters/character-edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public String updateCharacter(
            @PathVariable Integer id,
            @Valid @ModelAttribute("character") CharacterEntity character,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("races", raceService.getAllRaces());
            model.addAttribute("characterClasses", classService.getAllClasses());
            return "characters/character-edit";
        }

        CharacterEntity existingCharacter = characterService.getCharacterById(id);

        if (existingCharacter == null) {
            return "redirect:/characters";
        }

        character.setUserId(existingCharacter.getUserId());
        character.setCharacterId(id);
        character.setFlagged(existingCharacter.getFlagged());
        character.setCreatedAt(existingCharacter.getCreatedAt());

        characterService.saveCharacter(character);

        redirectAttributes.addFlashAttribute("successMessage", "Character updated successfully.");

        return "redirect:/characters/detail/" + character.getCharacterId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteCharacter(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        characterService.deleteCharacter(id);

        redirectAttributes.addFlashAttribute("successMessage", "Character deleted successfully.");

        return "redirect:/characters";
    }
}