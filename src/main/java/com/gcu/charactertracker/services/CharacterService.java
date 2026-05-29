/**
 * Service class containing business logic
 * related to character management.
 * 
 * Handles communication between the controller
 * and repository layers.
 * 
 * @author Daniel Louis
 * @version 1.0
 * 
 */

package com.gcu.charactertracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.repositories.CharacterRepository;

@Service
public class CharacterService 
{
    
    @Autowired
    private CharacterRepository characterRepository;
    
    /**
     * Retrieves a list of all characters from the database.
     * @return a list of CharacterEntity objects
     */
    public List<CharacterEntity> getAllCharacters()
    {
        return characterRepository.findAll();
    }
    
    /**
     * Retrieves a character by its ID.
     * @param id the ID of the character
     * @return the CharacterEntity object, or null if not found
     */
    public CharacterEntity getCharacterById(Long id)
    {
        Optional<CharacterEntity> character = characterRepository.findById(id);
        return character.orElse(null);
    }

    /**
     * Inserts or updates a character in the database.
     * JPA's save() method will handle both insert and update operations based on the presence of the character's ID.
     * @param character the CharacterEntity object to be saved or updated
     * @return the saved or updated CharacterEntity object
     */
    public CharacterEntity saveCharacter(CharacterEntity character)
    {
        return characterRepository.save(character);
    }

    /**
     * Deletes a character from the database by its ID.
     * @param id the ID of the character to delete
     */
    public void deleteCharacter(Long id)
    {
        characterRepository.deleteById(id);
    }

}