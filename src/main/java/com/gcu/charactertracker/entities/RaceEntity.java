/**
 * Entity class representing a race record
 * stored in the races database table.
 * 
 * This class contains race-related data
 * used throughout the application.
 * 
 * @author Daniel Louise
 * @version 1.0
 * 
 */
package com.gcu.charactertracker.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * The race table is used to automatically populate the race dropdown menu
 * when creating or editing a character. It contains a list of available races.
 */
@Entity
@Table(name = "races")
public class RaceEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_id")
    private Long raceId;

    @Column(name = "race_name")
    private String raceName;

    @Column(name = "race_description")
    private String raceDescription;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Default constructor for RaceEntity.
     */
    public RaceEntity()
    {

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
     * Gets the race name.
     * 
     * @return the race name
     */
    public String getRaceName() 
    {
        return raceName;
    }

    /**
     * Sets the race name.
     * 
     * @param raceName the race name to set
     */
    public void setRaceName(String raceName) 
    {
        this.raceName = raceName;
    }

    /**
     * Gets the race description.
     * 
     * @return the race description
     */
    public String getRaceDescription() 
    {
        return raceDescription;
    }

    /**
     * Sets the race description.
     * 
     * @param raceDescription the race description to set
     */ 
    public void setRaceDescription(String raceDescription) 
    {
        this.raceDescription = raceDescription;
    }

    /**
     * Gets the date and time when the race was added.
     *
     * @return the date and time when the race was added
     */
    public LocalDateTime getAddedAt() 
    {
        return addedAt;
    }

    /**
     * Sets the date and time when the race was added.
     *
     * @param addedAt the date and time when the race was added
     */
    public void setAddedAt(LocalDateTime addedAt) 
    {
        this.addedAt = addedAt;
    }

    /**
     * Gets the date and time when the race was last updated.
     *
     * @return the date and time when the race was last updated
     */
    public LocalDateTime getUpdatedAt() 
    {
        return updatedAt;
    }

    /**
     * Sets the date and time when the race was last updated.
     *
     * @param updatedAt the date and time when the race was last updated
     */
    public void setUpdatedAt(LocalDateTime updatedAt) 
    {
        this.updatedAt = updatedAt;
    }
}