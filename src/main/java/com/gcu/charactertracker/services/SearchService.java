package com.gcu.charactertracker.services;

import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final CharacterRepository characterRepository;

    public SearchService(
            CharacterRepository characterRepository) {

        this.characterRepository = characterRepository;
    }

    public List<Character> search(String keyword, String race, String characterClass) {

        if (keyword == null) keyword = "";
        if (race != null && race.isEmpty()) race = null;
        if (characterClass != null && characterClass.isEmpty()) characterClass = null;

        return repo.search(keyword, race, characterClass);
    }
}