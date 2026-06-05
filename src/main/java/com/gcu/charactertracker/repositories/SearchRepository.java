package com.gcu.charactertracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gcu.charactertracker.entities.CharacterEntity;

public interface SearchRepository extends JpaRepository<CharacterEntity, Integer> {
    @Query("""
        SELECT c FROM CharacterEntity c
        WHERE (:keyword IS NULL OR LOWER(c.characterName) LIKE LOWER(CONCAT('%', :keyword, '%')))
        AND (:race IS NULL
            OR c.race.raceName = :race)

        AND (:characterClass IS NULL
            OR c.characterClass.className = :characterClass)

        AND (:classType IS NULL
            OR c.characterClass.classType = :classType)
    """)

    List<CharacterEntity> search(
        String keyword,
        String race,
        String characterClass,
        String classType);



}