package com.toropov.oleg.entity;

import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.WorldMap;


/**
 * Abstract class representing a creature in the simulation.
 */
public abstract class Creature extends Entity {
    private static final int MAX_HEALTH = 20;
    private boolean skipNextMove = false;
    private int speed;
    private int healthPoints;
    private int generation;


    /**
     * Constructs a Creature with specified coordinates, speed, health points, and generation.
     *
     * @param coordinates the coordinates of the creature
     * @param speed       the speed of the creature
     * @param healthPoints the health points of the creature
     * @param generation   the generation of the creature
     */
    protected Creature(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates);
        this.speed = speed;
        this.healthPoints = healthPoints;
        this.generation = generation;
    }

    /**
     * Returns the speed of the creature.
     *
     * @return the speed of the creature
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the creature.
     *
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Returns the health points of the creature.
     *
     * @return the health points of the creature
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Sets the health points of the creature.
     *
     * @param healthPoints the health points to set
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Returns whether the creature should skip the next move.
     *
     * @return true if the creature should skip the next move, false otherwise
     */
    public boolean isSkipNextMove() {
        return skipNextMove;
    }

    /**
     * Sets whether the creature should skip the next move.
     *
     * @param skipNextMove true if the creature should skip the next move, false otherwise
     */
    public void setSkipNextMove(boolean skipNextMove) {
        this.skipNextMove = skipNextMove;
    }

    /**
     * Returns the generation of the creature.
     *
     * @return the generation of the creature
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Sets the generation of the creature.
     *
     * @param generation the generation to set
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }

    /**
     * Decreases the health points of the creature by one.
     */
    public void decreaseHealth() {
        setHealthPoints(getHealthPoints() - 1);
    }

    /**
     * Regenerates health for the creature.
     *
     * @param additionalHealthPoints the number of health points to add
     */
    protected void regenerateHealth(int additionalHealthPoints) {
        int healthPoints = getHealthPoints();
        setHealthPoints(Math.min(healthPoints + additionalHealthPoints, MAX_HEALTH));
    }

    /**
     * Moves the creature to the specified coordinates on the map.
     *
     * @param coordinates the target coordinates
     * @param map         the map on which the creature moves
     */
    public void move(Coordinates coordinates, WorldMap map) {
        Entity entity = map.getEntity(getCoordinates());
        map.removeEntity(getCoordinates());

        if (getHealthPoints() > 0) {
            map.setEntity(coordinates, entity);
            decreaseHealth();
        }
    }

    /**
     * Makes a move for the creature on the map. This method should be implemented by subclasses.
     *
     * @param map the map on which the creature moves
     */
    public abstract void makeMove(WorldMap map);

    /**
     * Checks if the square at the specified coordinates is available for the creature to move.
     *
     * @param coordinates the coordinates to check
     * @param map         the map on which to check the coordinates
     * @return true if the square is available for move, false otherwise
     */
    public boolean isSquareAvailableForMove(Coordinates coordinates, WorldMap map) {
        return map.isSquareEmpty(coordinates) && map.isWithinBounds(coordinates);
    }
}


