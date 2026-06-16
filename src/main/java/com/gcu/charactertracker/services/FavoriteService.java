package com.gcu.charactertracker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gcu.charactertracker.entities.FavoriteEntity;
import com.gcu.charactertracker.repositories.FavoriteRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<FavoriteEntity> getFavorites(Integer userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public List<Integer> getFavoriteCharacterIds(Integer userId) {
        return favoriteRepository.findByUserId(userId)
                .stream()
                .map(FavoriteEntity::getCharacterId)
                .toList();
    }

    public void addFavorite(Integer userId, Integer characterId) {

        if (favoriteRepository
                .findByUserIdAndCharacterId(userId, characterId)
                .isEmpty()) {

            FavoriteEntity favorite = new FavoriteEntity();

            favorite.setUserId(userId);
            favorite.setCharacterId(characterId);

            favoriteRepository.save(favorite);
        }
    }

    public void removeFavorite(Integer userId, Integer characterId) {

        favoriteRepository
                .findByUserIdAndCharacterId(userId, characterId)
                .ifPresent(favoriteRepository::delete);
    }

    public boolean isFavorite(Integer userId, Integer characterId) {

        return favoriteRepository
                .findByUserIdAndCharacterId(userId, characterId)
                .isPresent();
    }

    public boolean toggleFavorite(Integer userId, Integer characterId) {

        if (isFavorite(userId, characterId)) {
            removeFavorite(userId, characterId);
            return false;
        }

        addFavorite(userId, characterId);
        return true;
    }
}