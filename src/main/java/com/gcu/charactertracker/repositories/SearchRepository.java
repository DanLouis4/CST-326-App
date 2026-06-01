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
        AND (:race IS NULL OR c.race = :race)
        AND (:characterClass IS NULL OR c.characterClass = :characterClass)
    """)

    List<CharacterEntity> search(
        @Param("keyword") String keyword,
        @Param("race") String race,
        @Param("characterClass") String characterClass
    );



}