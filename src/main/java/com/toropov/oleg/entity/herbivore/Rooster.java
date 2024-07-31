package com.toropov.oleg.entity.herbivore;

import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;

import java.util.Map;

/**
 * Represents a rooster in the simulation.
 * Roosters can move towards grass or find a hen to reproduce.
 */
public class Rooster extends Chickens {

    /**
     * Constructs a Rooster with the specified coordinates, speed, health points, and generation.
     *
     * @param coordinates the coordinates of the rooster
     * @param speed the speed of the rooster
     * @param healthPoints the health points of the rooster
     * @param generation the generation of the rooster
     */
    public Rooster(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Checks if the specified square is available for the rooster to move into.
     * A square is considered available if it is empty, contains grass, or contains a hen when reproduction is needed.
     *
     * @param coordinates the coordinates of the square to check
     * @param map the world map
     * @return true if the square is available for the rooster to move into, false otherwise
     */
    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, WorldMap map) {
        boolean isEmptyOrIsGrass = super.isSquareAvailableForMove(coordinates, map);
        Map<EntityType, Integer> countEntities = map.countEntities();

        if (areEntitiesExceedingThreshold(countEntities)) {
            return isEmptyOrIsGrass;
        } else {
            return isEmptyOrIsGrass || map.getEntity(coordinates) instanceof Hen;
        }
    }
}
