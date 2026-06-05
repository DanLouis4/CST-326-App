package com.gcu.charactertracker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.entities.Favorite;
import com.gcu.charactertracker.repositories.FavoriteRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;


    public FavoriteService(
            FavoriteRepository favoriteRepository) {

        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> getFavorites(Integer userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public void addFavorite(CharacterEntity character) {

        Integer userId = 1; // Placeholder user ID since UserEntity is not implemented

        if (favoriteRepository
                .findByUserIdAndCharacterId(userId, character.getCharacterId())
                .isEmpty()) {

            Favorite favorite = new Favorite();

            favorite.setUserId(userId);
            favorite.setCharacterId(characterId);

            favoriteRepository.save(favorite);
        }
    }

    public void removeFavorite(CharacterEntity character) {

        Integer userId = 1; // Placeholder user ID since UserEntity is not implemented

        favoriteRepository
                .findByUserIdAndCharacterId(userId, characterId)
                .ifPresent(favoriteRepository::delete);
    }

    public boolean isFavorite(Integer userId, Integer characterId) {

    return favoriteRepository
            .findByUserIdAndCharacterId(userId, characterId)
            .isPresent();
    }
}