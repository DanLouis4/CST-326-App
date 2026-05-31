package com.gcu.charactertracker.services;

import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(
            FavoriteRepository favoriteRepository) {

        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> getFavorites(User user) {
        return favoriteRepository.findByUser(user);
    }

    public void addFavorite(
            User user,
            Character character) {

        if (favoriteRepository
                .findByUserAndCharacter(user, character)
                .isEmpty()) {

            Favorite favorite = new Favorite();

            favorite.setUser(user);
            favorite.setCharacter(character);

            favoriteRepository.save(favorite);
        }
    }
}