/**
 * Entity class representing a character record
 * stored in the characters database table.
 * 
 * This class maps character-related data used
 * throughout the application.
 * 
 * @author Daniel Louise
 * @version 1.0
 * 
 */
package com.gcu.charactertracker.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The CharacterEntity class represents a character record in the database.
 * It contains fields for character attributes such as name, level, gender, type, description, and timestamps for creation and updates.
 */
@Entity
@Table(name = "characters")
public class CharacterEntity 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "character_name")
    @NotBlank(message = "Character name is required")
    private String characterName;

    @Column(name = "character_level")
    @NotNull(message = "Character level is required")
    @Min(value = 1, message = "Character level must be at least 1")
    private Integer characterLevel;

    @Column(name = "character_gender")
    @NotBlank(message = "Please select a character gender")
    private String characterGender;

    @Column(name = "character_type")
    @NotBlank(message = "Please select a character type")
    private String characterType;

    @Column(name = "character_description")
    private String characterDescription;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "race_id")
    @NotNull(message = "Please select a race")
    private Long raceId;

    @Column(name = "class_id")
    @NotNull(message = "Please select a class")
    private Long classId;

    @Column(name = "visibility")
    private Integer visibility = 1; // Default visibility is public

    @Column(name = "flagged")
    private Boolean flagged = false; // Default flagged is false

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    /**
     * Default Constructor
     */
    public CharacterEntity() 
    {
        
    }
    
    /**
     * Gets the character ID.
     * 
     * @return the character ID
     */
    public Long getCharacterId() 
    {
        return characterId;
    }
    
    /**
     * Sets the character ID.
     * 
     * @param characterId the character ID to set
     */
    public void setCharacterId(Long characterId) 
    {
        this.characterId = characterId;
    }

    /**
     * Gets the character name.
     * 
     * @return the character name
     */
    public String getCharacterName() 
    {
        return characterName;
    }

    /**
     * Sets the character name.
     * 
     * @param characterName the character name to set
     */
    public void setCharacterName(String characterName) 
    {
        this.characterName = characterName;
    }

    /**
     * Gets the character level.
     * 
     * @return the character level
     */
    public Integer getCharacterLevel() 
    {
        return characterLevel;
    }

    /**
     * Sets the character level.
     * 
     * @param characterLevel the character level to set
     */
    public void setCharacterLevel(Integer characterLevel) 
    {
        this.characterLevel = characterLevel;
    }

    /**
     * Gets the character gender.
     * 
     * @return the character gender
     */
    public String getCharacterGender() 
    {
        return characterGender;
    }

    /**
     * Sets the character gender.
     * 
     * @param characterGender the character gender to set
     */
    public void setCharacterGender(String characterGender) 
    {
        this.characterGender = characterGender;
    }

    /**
     * Gets the character type.
     * 
     * @return the character type
     */
    public String getCharacterType() 
    {
        return characterType;
    }

    /**
     * Sets the character type.
     * 
     * @param characterType the character type to set
     */
    public void setCharacterType(String characterType) 
    {
        this.characterType = characterType;
    }

    /**
     * Gets the character description.
     * 
     * @return the character description
     */
    public String getCharacterDescription() 
    {
        return characterDescription;
    }

    /**
     * Sets the character description.
     * 
     * @param characterDescription the character description to set
     */
    public void setCharacterDescription(String characterDescription) 
    {
        this.characterDescription = characterDescription;
    }

    /**
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public Long getUserId() 
    {
        return userId;
    }

    /**
     * Sets the user ID.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    /**
     * Gets the race ID.
     * 
     * @return the race ID
     */
    public Long getRaceId() 
    {
        return raceId;
    }

    /**
     * Sets the race ID.
     * 
     * @param raceId the race ID to set
     */
    public void setRaceId(Long raceId) 
    {
        this.raceId = raceId;
    }

    /**
     * Gets the class ID.
     * 
     * @return the class ID
     */
    public Long getClassId() 
    {
        return classId;
    }

    /**
     * Sets the class ID.
     * 
     * @param classId the class ID to set
     */
    public void setClassId(Long classId) 
    {
        this.classId = classId;
    }

    public Integer getVisibility() 
    {
        return visibility;
    }

    public void setVisibility(Integer visibility) 
    {
        this.visibility = visibility;
    }

    /**
     * Gets the flagged status.
     * 
     * @return the flagged status
     */
    public Boolean getFlagged() 
    {
        return flagged;
    }

    /**
     * Sets the flagged status.
     * 
     * @param flagged the flagged status to set
     */
    public void setFlagged(Boolean flagged) 
    {
        this.flagged = flagged;
    }

    /**
     * Gets the image URL.
     * 
     * @return the image URL
     */
    public String getImageUrl() 
    {
        return imageUrl;
    }

    /**
     * Sets the image URL.
     * 
     * @param imageUrl the image URL to set
     */
    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the creation timestamp.
     * 
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() 
    {
        return createdAt;
    }

    /**
     * Sets the creation timestamp.
     * 
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) 
    {
        this.createdAt = createdAt;
    }

    /**
     * Gets the update timestamp.
     * 
     * @return the update timestamp
     */
    public LocalDateTime getUpdatedAt() 
    {
        return updatedAt;
    }

    /**
     * Sets the update timestamp.
     * 
     * @param updatedAt the update timestamp to set
     */
    public void setUpdatedAt(LocalDateTime updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

}