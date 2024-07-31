package com.toropov.oleg.entity.herbivore;

import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;

import java.util.Map;

/**
 * Represents a hen in the simulation.
 * Hens can move towards grass or find a rooster to reproduce.
 */
public class Hen extends Chickens {

    /**
     * Constructs a Hen with the specified coordinates, speed, health points, and generation.
     *
     * @param coordinates the coordinates of the hen
     * @param speed the speed of the hen
     * @param healthPoints the health points of the hen
     * @param generation the generation of the hen
     */
    public Hen(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Checks if the specified square is available for the hen to move into.
     * A square is considered available if it is empty, contains grass, or contains a rooster when reproduction is needed.
     *
     * @param coordinates the coordinates of the square to check
     * @param map the world map
     * @return true if the square is available for the hen to move into, false otherwise
     */
    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, WorldMap map) {
        boolean isEmptyOrIsGrass = super.isSquareAvailableForMove(coordinates, map);
        Map<EntityType, Integer> countEntities = map.countEntities();

        if (areEntitiesExceedingThreshold(countEntities)) {
            return isEmptyOrIsGrass;
        } else {
            return isEmptyOrIsGrass || map.getEntity(coordinates) instanceof Rooster;
        }
    }
}
