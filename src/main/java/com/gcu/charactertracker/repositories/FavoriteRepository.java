package com.gcu.charactertracker.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcu.charactertracker.entities.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findByUserId(Integer userId);

    Optional<Favorite> findByUserIdAndCharacterId(Integer userId, Integer characterId);

}