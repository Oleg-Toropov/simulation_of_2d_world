package com.toropov.oleg.entity.herbivore;

import com.toropov.oleg.entity.Creature;
import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.entity.EntityFactory;
import com.toropov.oleg.entity.Grass;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;
import com.toropov.oleg.map.WorldMapUtils;

import java.util.List;
import java.util.Map;

/**
 * Represents a herbivore in the simulation.
 * Herbivores can move towards grass or find a mate to reproduce.
 */
public abstract class Herbivore extends Creature {

    /**
     * Constructs a Herbivore with the specified coordinates, speed, health points, and generation.
     *
     * @param coordinates the coordinates of the herbivore
     * @param speed the speed of the herbivore
     * @param healthPoints the health points of the herbivore
     * @param generation the generation of the herbivore
     */
    public Herbivore(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Checks if the square at the given coordinates is available for the herbivore to move into.
     * A square is considered available if it is empty or contains grass.
     *
     * @param coordinates the coordinates of the square to check
     * @param map the world map
     * @return true if the square is available for move, false otherwise
     */
    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, WorldMap map) {
        boolean isEmpty = super.isSquareAvailableForMove(coordinates, map);
        boolean isGrass = map.getEntity(coordinates) instanceof Grass;

        return isEmpty || isGrass;
    }

    /**
     * Finds the nearest grass or a potential mate for the herbivore.
     * If mating is needed, prioritizes finding a mate over grass.
     *
     * @param map the world map
     * @return the coordinates of the nearest grass or mate, or null if none found
     */
    protected Coordinates findNearestGrassOrCouple(WorldMap map) {
        Coordinates start = getCoordinates();
        List<int[]> shifts = WorldMapUtils.getAllSortedShiftsForCoordinates(map.getMapSize());

        for (int[] shift : shifts) {
            Coordinates neighbor = new Coordinates(start.getX() + shift[0], start.getY() + shift[1]);

            if (map.isWithinBounds(neighbor)) {
                Entity entity = map.getEntity(neighbor);
                if (needsMating(map)) {
                    Entity currentEntity = map.getEntity(start);
                    if (currentEntity instanceof Hen && entity instanceof Rooster ||
                            currentEntity instanceof Rooster && entity instanceof Hen) {
                        return neighbor;
                    }
                } else if (entity instanceof Grass) {
                    return neighbor;
                }
            }
        }

        return null;
    }

    /**
     * Checks if the herbivore needs to find a mate based on the current entity counts in the map.
     *
     * @param map the world map
     * @return true if mating is needed, false otherwise
     */
    private boolean needsMating(WorldMap map) {
        Map<EntityType, Integer> countEntities = map.countEntities();
        boolean roosterDeficit = countEntities.get(EntityType.ROOSTER) <= EntityFactory.ROOSTER_COUNT / 2 &&
                countEntities.get(EntityType.ROOSTER) > 0 &&
                countEntities.get(EntityType.HEN) >= 2 &&
                countEntities.get(EntityType.CHICK) == 0;
        boolean henDeficit = countEntities.get(EntityType.HEN) <= EntityFactory.HEN_COUNT / 2 &&
                countEntities.get(EntityType.HEN) > 0 &&
                countEntities.get(EntityType.ROOSTER) >= 2 &&
                countEntities.get(EntityType.CHICK) == 0;

        return roosterDeficit || henDeficit;
    }
}


