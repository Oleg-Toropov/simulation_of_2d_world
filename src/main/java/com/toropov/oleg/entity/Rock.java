package com.toropov.oleg.entity;

import com.toropov.oleg.map.Coordinates;

/**
 * Represents a rock entity in the world map.
 */
public class Rock extends Entity{

    /**
     * Constructs a new Rock with the given coordinates.
     *
     * @param coordinates the coordinates of the rock
     */
    public Rock(Coordinates coordinates) {
        super(coordinates);
    }
}
