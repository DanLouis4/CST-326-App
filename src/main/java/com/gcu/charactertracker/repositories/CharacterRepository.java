/**
 * Repository interface used to perform CRUD
 * operations on CharacterEntity objects.
 * 
 * Extends JpaRepository for built-in database operations.
 * 
 * @author Daniel Louis
 * @version 1.0
 * 
 */

package com.gcu.charactertracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.charactertracker.entities.CharacterEntity;

/**
 * The CharacterRepository interface provides methods for performing CRUD operations
 * on CharacterEntity objects. It extends JpaRepository, which provides built-in
 * methods for common database operations such as save, findById, findAll, and delete.
 */

public interface CharacterRepository extends JpaRepository<CharacterEntity, Integer> 
{

    /**
     * Finds characters whose names contain the specified keyword, ignoring case.
     *
     * @param keyword the keyword to search for in character names
     * @return a list of characters matching the search criteria
     */
    List<CharacterEntity> findByCharacterNameContainingIgnoreCase(String keyword);

    /**
     * Finds characters by their user ID.
     *
     * @param userId the user ID
     * @return a list of characters created by the specified user
     */
    List<CharacterEntity> findByUserId(Integer userId);

    /**
     * Finds characters by their IDs.
     *
     * @param characterIds the list of character IDs
     * @return a list of characters with the specified IDs
     */
    List<CharacterEntity> findByCharacterIdIn(List<Integer> characterIds);

    /**
     * Finds characters by their visibility status.
     *
     * @param visibility the visibility status
     * @return a list of characters with the specified visibility
     */
    List<CharacterEntity> findByVisibility(Integer visibility);

    /**
     * Finds characters by their visibility status or user ID.
     *
     * @param visibility the visibility status
     * @param userId the user ID
     * @return a list of characters matching the specified criteria
     */
    List<CharacterEntity> findByVisibilityOrUserId(Integer visibility, Integer userId);

}