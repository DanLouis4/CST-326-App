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
import org.springframework.stereotype.Repository;

import com.gcu.charactertracker.entities.CharacterEntity;

/**
 * The CharacterRepository interface provides methods for performing CRUD operations
 * on CharacterEntity objects. It extends JpaRepository, which provides built-in
 * methods for common database operations such as save, findById, findAll, and delete.
 */
@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Integer> 
{

    List<CharacterEntity> findByNameContainingIgnoreCase(String name);

}