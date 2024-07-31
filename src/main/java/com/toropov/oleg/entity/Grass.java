package com.toropov.oleg.entity;

import com.toropov.oleg.map.Coordinates;

/**
 * Represents a grass entity on the map.
 */
public class Grass extends Entity{

    /**
     * Constructs a new Grass entity at the specified coordinates.
     *
     * @param coordinates the coordinates of the grass entity
     */
    public Grass(Coordinates coordinates) {
        super(coordinates);
    }
}
