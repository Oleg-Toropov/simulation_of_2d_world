package com.toropov.oleg.entity;

import com.toropov.oleg.map.Coordinates;

/**
 * Represents a tree entity in the world map.
 */
public class Tree extends Entity{

    /**
     * Constructs a new Tree with the given coordinates.
     *
     * @param coordinates the coordinates of the tree
     */
    public Tree(Coordinates coordinates) {
        super(coordinates);
    }
}
