/**
 * Service class containing business logic
 * related to character races.
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

import com.gcu.charactertracker.entities.RaceEntity;
import com.gcu.charactertracker.repositories.RaceRepository;

@Service
public class RaceService
{

    private final RaceRepository raceRepository;

    /**
     * Constructs a new RaceService with the specified RaceRepository.
     * @param raceRepository the RaceRepository to be used by this service
     */
    public RaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    /**
     * Retrieves a list of all races from the database.
     * @return a list of RaceEntity objects
     */
    public List<RaceEntity> getAllRaces() 
    {
        return raceRepository.findAll();
    }

    /**
     * Retrieves a race by its ID
     * @param id the ID of the race
     * @return an Optional containing the RaceEntity if found, or empty if not found
     */
    public Optional<RaceEntity> getRaceById(Long id) 
    {
        return raceRepository.findById(id);
    }
}