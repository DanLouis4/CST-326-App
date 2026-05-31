/**
 * Controller responsible for handling
 * character-related web requests.
 * 
 * Provides CRUD operations for characters.
 * 
 * @author Daniel Louis
 * @version 1.0
 * 
 */
package com.gcu.charactertracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.gcu.charactertracker.services.CharacterService;
import com.gcu.charactertracker.services.ClassService;
import com.gcu.charactertracker.services.RaceService;

import jakarta.validation.Valid;

/**
 * The CharacterController class is responsible for handling web requests related to character management.
 * It provides methods for displaying a list of characters, showing forms for creating and editing characters,
 * and processing form submissions to create or update character records in the database.
 * The controller interacts with the CharacterService, RaceService, and ClassService to perform business logic and data access operations.
 */
@Controller
@RequestMapping("/characters")
public class CharacterController 
{
    @Autowired
    private CharacterService characterService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private ClassService classService;

    /**
     * Displays a list of all characters.
     * @param model the Model object to pass data to the view
     * @return the name of the view template to render
     */
    @GetMapping
    public String showCharacters(Model model)
    {
        model.addAttribute("characters", characterService.getAllCharacters());
        return "characters/characters";
    }

    /**
     * Displays the form for creating a new character.
     * @param model the Model object to pass data to the view
     * @return the name of the view template to render
     */
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("character", new CharacterEntity());
        model.addAttribute("races", raceService.getAllRaces());
        model.addAttribute("characterClasses", classService.getAllClasses());

        return "characters/character-create";
    }

    /**
     * Processes the form submission for creating a new character.
     * @param character the CharacterEntity object populated from the form
     * @param bindingResult the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param redirectAttributes the RedirectAttributes object to pass flash attributes
     * @return a redirect to the list of characters
     */
    @PostMapping("/create")
    public String createCharacter(@Valid @ModelAttribute("character") CharacterEntity character, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("races", raceService.getAllRaces());
            model.addAttribute("characterClasses", classService.getAllClasses());
            return "characters/character-create";
        }

        character.setFlagged(false); // Set default value for flagged field at creation
        character.setUserId(1); // Set default user ID for the character until user authentication is implemented
        characterService.saveCharacter(character);

        redirectAttributes.addFlashAttribute("successMessage", "Character created successfully.");

        return "redirect:/characters/detail/" + character.getCharacterId();
    }

    /**
     * Displays the details of a specific character.
     * @param id the ID of the character
     * @param model the Model object to pass data to the view
     * @return the name of the view template to render
     */
    @GetMapping("/detail/{id}")
    public String showCharacterDetail(@PathVariable Integer id, Model model)
    {
        CharacterEntity character = characterService.getCharacterById(id);

        if (character == null)
        {
            return "redirect:/characters";
        }

        model.addAttribute("character", character);
        return "characters/character-detail";
    }

    /**
     * Displays the form for editing an existing character.
     * @param id the ID of the character
     * @param model the Model object to pass data to the view
     * @return the name of the view template to render
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model)
    {
        CharacterEntity character = characterService.getCharacterById(id);

        if (character == null)
        {
            return "redirect:/characters";
        }

        model.addAttribute("character", character);
        model.addAttribute("races", raceService.getAllRaces());
        model.addAttribute("characterClasses", classService.getAllClasses());
        return "characters/character-edit";
    }

    /**
     * Processes the form submission for updating an existing character.
     * @param id the ID of the character
     * @param character the CharacterEntity object populated from the form
     * @param bindingResult the BindingResult object to check for validation errors
     * @param model the Model object to pass data to the view
     * @param redirectAttributes the RedirectAttributes object to pass flash attributes
     * @return a redirect to the list of characters
     */
    @PostMapping("/edit/{id}")
    public String updateCharacter(@PathVariable Integer id, @Valid @ModelAttribute("character") CharacterEntity character, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("races", raceService.getAllRaces());
            model.addAttribute("characterClasses", classService.getAllClasses());
            return "characters/character-edit";
        }
        
        CharacterEntity existingCharacter = characterService.getCharacterById(id);

        /* Check if the character exists */
        if (existingCharacter == null)
        {
            return "redirect:/characters";
        }

        /* Preserve the original values for fields that should not be changed  */
        character.setUserId(existingCharacter.getUserId()); // Preserve the original user ID
        character.setCharacterId(id); // Ensure the character ID is set for the update operation
        character.setFlagged(existingCharacter.getFlagged()); // Preserve the original flagged status
        character.setCreatedAt(existingCharacter.getCreatedAt()); // Preserve the original creation timestamp

        characterService.saveCharacter(character);
        redirectAttributes.addFlashAttribute("successMessage", "Character updated successfully.");
        return "redirect:/characters/detail/" + character.getCharacterId();
    }

    /**
     * Deletes a character by its ID.
     * @param id the ID of the character to delete
     * @param redirectAttributes the RedirectAttributes object to pass flash attributes
     * @return a redirect to the list of characters
     */
    @GetMapping("/delete/{id}")
    public String deleteCharacter(@PathVariable Integer id, RedirectAttributes redirectAttributes)
    {
        characterService.deleteCharacter(id);
        redirectAttributes.addFlashAttribute("successMessage", "Character deleted successfully.");
        return "redirect:/characters";
    }
}