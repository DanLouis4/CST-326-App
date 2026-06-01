/**
 * Repository interface used to perform CRUD
 * operations on ClassEntity objects.
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
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gcu.charactertracker.entities.ClassEntity;

/**
 * The ClassRepository interface provides methods for performing CRUD operations
 * on ClassEntity objects. It extends JpaRepository, which provides built-in
 * methods for common database operations such as save, findById, findAll, and delete.
 */
@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> 
{

    /**
     * Retrieves a list of distinct class types from the database.
     * @return a list of distinct class types
     */
    @Query("""
        SELECT DISTINCT c.classType
        FROM ClassEntity c
        WHERE c.classType IS NOT NULL
    """)
    List<String> findDistinctClassTypes();

}