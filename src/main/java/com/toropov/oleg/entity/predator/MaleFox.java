package com.toropov.oleg.entity.predator;

import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;

import java.util.Map;

/**
 * Represents a male fox in the simulation.
 * Male foxes can move towards herbivores or find a mate to reproduce.
 */
public class MaleFox extends Foxes{

    /**
     * Constructs a MaleFox with the specified coordinates, speed, health points, and generation.
     *
     * @param coordinates the coordinates of the male fox
     * @param speed the speed of the male fox
     * @param healthPoints the health points of the male fox
     * @param generation the generation of the male fox
     */
    public MaleFox(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Checks if the square at the given coordinates is available for the male fox to move into.
     * A square is considered available if it is empty, contains a herbivore, or (if necessary) contains a female fox.
     *
     * @param coordinates the coordinates of the square to check
     * @param map the world map
     * @return true if the square is available for move, false otherwise
     */
    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, WorldMap map) {
        boolean isEmptyOrIsHerbivore = super.isSquareAvailableForMove(coordinates, map);
        Map<EntityType, Integer> countEntities = map.countEntities();

        if (areEntitiesExceedingThreshold(countEntities)) {
            return isEmptyOrIsHerbivore;
        } else {
            return isEmptyOrIsHerbivore || map.getEntity(coordinates) instanceof FemaleFox;
        }
    }
}
