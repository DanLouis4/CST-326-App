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
        String classType)
{
    return searchRepository.search(
            keyword,
            race,
            characterClass,
            classType);
}
}