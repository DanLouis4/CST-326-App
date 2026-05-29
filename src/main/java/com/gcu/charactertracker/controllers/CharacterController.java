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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.services.CharacterService;
import com.gcu.charactertracker.services.ClassService;
import com.gcu.charactertracker.services.RaceService;

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
     * @return a redirect to the list of characters
     */
    @PostMapping("/create")
    public String createCharacter(@ModelAttribute("character") CharacterEntity character)
    {
        character.setFlagged(false); // Set default value for flagged field at creation
        character.setUserId(1L); // Set default user ID for the character until user authentication is implemented
        characterService.saveCharacter(character);
        return "redirect:/characters";
    }

    /**
     * Displays the details of a specific character.
     * @param id the ID of the character
     * @param model the Model object to pass data to the view
     * @return the name of the view template to render
     */
    @GetMapping("/detail/{id}")
    public String showCharacterDetail(@PathVariable Long id, Model model)
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
    public String showEditForm(@PathVariable Long id, Model model)
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
     * @return a redirect to the list of characters
     */
    @PostMapping("/edit/{id}")
    public String updateCharacter(@PathVariable Long id, @ModelAttribute("character") CharacterEntity character)
    {
        character.setCharacterId(id); // Ensure the character ID is set for the update operation
        characterService.saveCharacter(character);
        return "redirect:/characters";
    }

    /**
     * Deletes a character by its ID.
     * @param id the ID of the character to delete
     * @return a redirect to the list of characters
     */
    @GetMapping("/delete/{id}")
    public String deleteCharacter(@PathVariable Long id)
    {
        characterService.deleteCharacter(id);
        return "redirect:/characters";
    }
}