package com.toropov.oleg.entity;

import com.toropov.oleg.map.Coordinates;

/**
 * Abstract class representing an entity in the simulation.
 */
public abstract class Entity {
    private Coordinates coordinates;

    /**
     * Constructs an Entity with specified coordinates.
     *
     * @param coordinates the coordinates of the entity
     */
    public Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns the coordinates of the entity.
     *
     * @return the coordinates of the entity
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates of the entity.
     *
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
