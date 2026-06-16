package com.gcu.charactertracker.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.charactertracker.entities.FavoriteEntity;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

    List<FavoriteEntity> findByUserId(Integer userId);

    Optional<FavoriteEntity> findByUserIdAndCharacterId(Integer userId, Integer characterId);
    
}