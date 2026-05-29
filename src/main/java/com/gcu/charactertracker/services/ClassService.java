/**
 * Service class containing business logic
 * related to character classes.
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

import org.springframework.stereotype.Service;

import com.gcu.charactertracker.entities.ClassEntity;
import com.gcu.charactertracker.repositories.ClassRepository;

@Service
public class ClassService
{

    private final ClassRepository classRepository;

    /**
     * Constructs a new ClassService with the specified ClassRepository.
     * @param classRepository the ClassRepository to be used by this service
     */
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    /**
     * Retrieves a list of all classes from the database.
     * @return a list of ClassEntity objects
     */
    public List<ClassEntity> getAllClasses() 
    {
        return classRepository.findAll();
    }

    /**
     * Retrieves a class by its ID
     * @param id the ID of the class
     * @return an Optional containing the ClassEntity if found, or empty if not found
     */
    public Optional<ClassEntity> getClassById(Long id) 
    {
        return classRepository.findById(id);
    }
}