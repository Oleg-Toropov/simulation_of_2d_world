package com.toropov.oleg.entity.predator;

import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;

import java.util.Map;

/**
 * Represents a female fox in the simulation.
 * Female foxes can move towards herbivores or find a mate to reproduce.
 */
public class FemaleFox extends Foxes{

    /**
     * Constructs a new FemaleFox.
     *
     * @param coordinates the initial coordinates of the female fox
     * @param speed the speed of the female fox
     * @param healthPoints the health points of the female fox
     * @param generation the generation of the female fox
     */
    public FemaleFox(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Checks if the specified square is available for the female fox to move.
     * A square is available if it is empty, contains a herbivore, or, under certain conditions, contains a male fox.
     *
     * @param coordinates the coordinates of the square to check
     * @param map the world map
     * @return true if the square is available for movement, false otherwise
     */
    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, WorldMap map) {
        boolean isEmptyOrIsHerbivore = super.isSquareAvailableForMove(coordinates, map);
        Map<EntityType, Integer> countEntities = map.countEntities();

        if (areEntitiesExceedingThreshold(countEntities)) {
            return isEmptyOrIsHerbivore;
        } else {
            return isEmptyOrIsHerbivore || map.getEntity(coordinates) instanceof MaleFox;
        }
    }
}
