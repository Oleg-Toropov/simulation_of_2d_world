package com.toropov.oleg.entity.predator;

import com.toropov.oleg.entity.Creature;
import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.entity.herbivore.Hen;
import com.toropov.oleg.entity.herbivore.Herbivore;
import com.toropov.oleg.entity.herbivore.Rooster;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;
import com.toropov.oleg.map.WorldMapUtils;

import java.util.List;
import java.util.Map;

/**
 * Represents a predator in the simulation.
 * Predators can move towards herbivores to hunt or potential mates to reproduce.
 */
public abstract class Predator extends Creature {

    /**
     * Constructs a new Predator.
     *
     * @param coordinates the initial coordinates of the predator
     * @param speed the speed of the predator
     * @param healthPoints the health points of the predator
     * @param generation the generation of the predator
     */
    public Predator(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Checks if a given square is available for the predator to move into.
     * A square is available if it is empty or contains a hen or rooster.
     *
     * @param coordinates the coordinates of the square to check
     * @param map the world map
     * @return true if the square is available for move, false otherwise
     */
    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, WorldMap map) {
        boolean isEmpty = super.isSquareAvailableForMove(coordinates, map);
        boolean isHen = map.getEntity(coordinates) instanceof Hen;
        boolean isRooster = map.getEntity(coordinates) instanceof Rooster;

        return isEmpty || isHen || isRooster;
    }

    /**
     * Finds the nearest herbivore or a suitable mate for the predator.
     *
     * @param map the world map
     * @return the coordinates of the nearest herbivore or mate, or null if none found
     */
    protected Coordinates findNearestHerbivoreOrCouple(WorldMap map) {
        Coordinates start = getCoordinates();
        List<int[]> shifts = WorldMapUtils.getAllSortedShiftsForCoordinates(map.getMapSize());

        for (int[] shift : shifts) {
            Coordinates neighbor = new Coordinates(start.getX() + shift[0], start.getY() + shift[1]);

            if (map.isWithinBounds(neighbor)) {
                Entity entity = map.getEntity(neighbor);
                if (needsMating(map)) {
                    Entity currentEntity = map.getEntity(start);
                    if (currentEntity instanceof FemaleFox && entity instanceof MaleFox ||
                            currentEntity instanceof MaleFox && entity instanceof FemaleFox) {
                        return neighbor;
                    }
                } else if (entity instanceof Herbivore) {
                    return neighbor;
                }
            }
        }

        return null;
    }

    /**
     * Checks if there is a need for mating based on the current map state.
     * Mating is needed if there is exactly one male fox, one female fox, and no fox cubs.
     *
     * @param map the world map
     * @return true if mating is needed, false otherwise
     */
    private boolean needsMating(WorldMap map) {
        Map<EntityType, Integer> countEntities = map.countEntities();

        return countEntities.get(EntityType.MALE_FOX) == 1 && countEntities.get(EntityType.FEMALE_FOX) == 1 &&
                countEntities.get(EntityType.FOX_CUB) == 0;
    }
}
