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
import com.gcu.charactertracker.services.CharacterAccessService;
import com.gcu.charactertracker.services.CharacterService;
import com.gcu.charactertracker.services.ClassService;
import com.gcu.charactertracker.services.FavoriteService;
import com.gcu.charactertracker.services.RaceService;
import com.gcu.charactertracker.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    private final UserService userService;
  
    private final CharacterService characterService;
    
    private final RaceService raceService;

    private final ClassService classService;

    private final CharacterAccessService characterAccessService;
    
    private final FavoriteService favoriteService;

    public CharacterController(CharacterService characterService, UserService userService, RaceService raceService, ClassService classService, CharacterAccessService characterAccessService, FavoriteService favoriteService) {
        this.characterService = characterService;
        this.userService = userService;
        this.raceService = raceService;
        this.classService = classService;
        this.characterAccessService = characterAccessService;
        this.favoriteService = favoriteService;
    }

    /**
     * Displays a list of characters visible to the user.
     * 
     * Logged-out users see only public characters.
     * Logged-in users see public characters plus their own private characters.
     * Admin users see all characters.
     * 
     * @param model
     * @param authentication
     * @return
     */
    @GetMapping
    public String showCharacters(Model model, Authentication authentication) {

        Integer loggedInUserId = characterAccessService.getLoggedInUserId(authentication);

        if (loggedInUserId != null) {
            model.addAttribute("favoriteCharacterIds", favoriteService.getFavoriteCharacterIds(loggedInUserId));
        } else {
            model.addAttribute("favoriteCharacterIds", java.util.List.of());
        }

        if (characterAccessService.isAdmin(authentication)) {
            model.addAttribute("characters", characterService.getAllCharacters());
            return "characters/characters";
        }

        model.addAttribute("characters", characterService.getVisibleCharactersForUser(loggedInUserId));

        return "characters/characters";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my")
    public String showMyCharacters(
            Model model,
            Authentication authentication) {

        Integer loggedInUserId = characterAccessService.getLoggedInUserId(authentication);

        model.addAttribute("characters", characterService.getCharactersByUserId(loggedInUserId));
        model.addAttribute("favoriteCharacterIds", favoriteService.getFavoriteCharacterIds(loggedInUserId));

        return "characters/my-characters";
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

    /**
     * Displays the details of a character if the user has permission to view it.
     * 
     * Logged-out users see only public characters.
     * Logged-in users see public characters plus their own private characters.
     * Admin users see all characters.
     * 
     * If the character is private and the user does not have permission to view it, they are redirected back to the character list with an error message.
     * 
     * @param id the ID of the character
     * @param model the model to pass data to the view
     * @param redirectAttributes used to pass flash attributes for redirection
     * @param authentication the authentication object representing the current user
     * @return the view name
     */
    @GetMapping("/detail/{id}")
    public String showCharacterDetail(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes,
            Authentication authentication) {

        CharacterEntity character = characterService.getCharacterById(id);

        if (!characterAccessService.canViewCharacter(character, authentication)) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Character is marked Private and unavailable to view.");

            return "redirect:/characters";
        }

        Integer loggedInUserId = characterAccessService.getLoggedInUserId(authentication);

        /* Add favorite character IDs to the model */
        if (loggedInUserId != null) {
            model.addAttribute("favoriteCharacterIds", favoriteService.getFavoriteCharacterIds(loggedInUserId));
        } else {
            model.addAttribute("favoriteCharacterIds", java.util.List.of());
        }

        model.addAttribute("character", character);
        return "characters/character-detail";
    }

    /**
     * Displays the edit form for a character if the user has permission to manage it.
     * Only the owner of the character can edit it.
     * 
     * @param id the ID of the character to edit
     * @param model the model to pass data to the view
     * @param authentication the authentication object representing the current user
     * @return the view name
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes,
            Authentication authentication) {

        CharacterEntity character = characterService.getCharacterById(id);

        if (!characterAccessService.canManageCharacter(character, authentication)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You do not have permission to edit this character.");
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
            RedirectAttributes redirectAttributes,
            Authentication authentication) {

        CharacterEntity existingCharacter = characterService.getCharacterById(id);

        if (!characterAccessService.canManageCharacter(existingCharacter, authentication)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You do not have permission to edit this character.");
            return "redirect:/characters";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("races", raceService.getAllRaces());
            model.addAttribute("characterClasses", classService.getAllClasses());
            return "characters/character-edit";
        }

        character.setUserId(existingCharacter.getUserId());
        character.setCharacterId(id);
        character.setFlagged(existingCharacter.getFlagged());
        character.setCreatedAt(existingCharacter.getCreatedAt());

        characterService.saveCharacter(character);

        redirectAttributes.addFlashAttribute("successMessage", "Character updated successfully.");

        return "redirect:/characters/detail/" + character.getCharacterId();
    }

    /**
     * Deletes a character if the user has permission to manage it.
     * Only the owner of the character can delete it.
     * 
     * @param id the ID of the character to delete
     * @param redirectAttributes used to pass flash attributes for redirection
     * @param authentication the authentication object representing the current user
     * @return the view name
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteCharacter(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes,
            Authentication authentication) {

        CharacterEntity character = characterService.getCharacterById(id);

        if (!characterAccessService.canManageCharacter(character, authentication)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You do not have permission to delete this character.");
            return "redirect:/characters";
        }

        characterService.deleteCharacter(id);

        redirectAttributes.addFlashAttribute("successMessage", "Character deleted successfully.");

        return "redirect:/characters";
    }

}