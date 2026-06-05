package com.gcu.charactertracker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.repositories.SearchRepository;
@Service
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<CharacterEntity> search(
        String keyword,
        String race,
        String characterClass,
        String classType) {

    if (keyword != null && keyword.isBlank()) {
        keyword = null;
    }

    if (race != null && race.isBlank()) {
        race = null;
    }

    if (characterClass != null && characterClass.isBlank()) {
        characterClass = null;
    }

    if (classType != null && classType.isBlank()) {
        classType = null;
    }

    return searchRepository.search(
            keyword,
            race,
            characterClass,
            classType);
    }
}