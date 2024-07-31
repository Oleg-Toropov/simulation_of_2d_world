package com.toropov.oleg.entity.predator;

import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.entity.EntityFactory;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;
import com.toropov.oleg.pathfinding.PathFinder;

import java.util.List;
import java.util.Map;

/**
 * Represents a fox in the simulation. Foxes can hunt herbivores or find a mate to reproduce.
 */
public abstract class Foxes extends Predator {
    private static final int ADDITIONAL_HEALTH = 5;

    /**
     * Constructs a new Fox.
     *
     * @param coordinates the initial coordinates of the fox
     * @param speed the speed of the fox
     * @param healthPoints the health points of the fox
     * @param generation the generation of the fox
     */
    public Foxes(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Checks if the fox population exceeds the predefined threshold.
     *
     * @param countEntities the map of entity counts
     * @return true if the fox population exceeds the threshold, false otherwise
     */
    protected boolean areEntitiesExceedingThreshold(Map<EntityType, Integer> countEntities) {
        return countEntities.get(EntityType.FEMALE_FOX) > EntityFactory.FEMALE_FOX_COUNT * 2 &&
                countEntities.get(EntityType.MALE_FOX) > EntityFactory.MALE_FOX_COUNT * 2;
    }

    /**
     * Makes a move for the fox in the world map. The fox can regenerate health, hunt herbivores, or find a mate.
     *
     * @param map the world map
     */
    @Override
    public void makeMove(WorldMap map) {
        if (isSkipNextMove()) {
            regenerateHealth(ADDITIONAL_HEALTH);
            setSkipNextMove(false);
        } else {
            Coordinates target = findNearestHerbivoreOrCouple(map);
            if (target != null) {
                List<Coordinates> path = PathFinder.findPathAStar(map, getCoordinates(), target, this);
                if (!path.isEmpty()) {
                    Coordinates nextMove = path.get(0);
                    handleMove(map, nextMove, target);
                }
            }
        }
    }

    /**
     * Handles the movement of the fox. If the fox encounters another fox, it will check for mating.
     *
     * @param map the world map
     * @param nextMove the next move coordinates
     * @param target the target coordinates
     */
    private void handleMove(WorldMap map, Coordinates nextMove, Coordinates target) {
        if (map.getEntity(nextMove) instanceof FemaleFox || map.getEntity(nextMove) instanceof MaleFox) {
            checkForCreateFoxCub(map, nextMove);
        } else {
            move(nextMove, map);
        }

        if (nextMove.equals(target)) {
            setSkipNextMove(true);
        }
    }

    /**
     * Checks if a fox cub can be created at the given coordinates.
     *
     * @param map the world map
     * @param nextMove the coordinates of the next move
     */
    protected void checkForCreateFoxCub(WorldMap map, Coordinates nextMove) {
        Entity entityNextMove = map.getEntity(nextMove);
        Entity entityCurrentPos = map.getEntity(getCoordinates());

        if (entityNextMove instanceof FemaleFox femaleFox && entityCurrentPos instanceof MaleFox maleFox) {
            createFoxCub(map, nextMove, femaleFox, maleFox);
        } else if (entityNextMove instanceof MaleFox maleFox && entityCurrentPos instanceof FemaleFox femaleFox) {
            createFoxCub(map, nextMove, femaleFox, maleFox);
        }
    }

    /**
     * Creates a fox cub at the given coordinates.
     *
     * @param map the world map
     * @param nextMove the coordinates of the next move
     * @param femaleFox the female fox
     * @param maleFox the male fox
     */
    private void createFoxCub(WorldMap map, Coordinates nextMove, FemaleFox femaleFox, MaleFox maleFox) {
        map.removeEntity(getCoordinates());
        map.removeEntity(nextMove);

        int generationFoxCub = Math.max(maleFox.getGeneration(), femaleFox.getGeneration()) + 1;
        FoxCub foxCub = new FoxCub(nextMove, 0, 2, generationFoxCub);

        foxCub.setParents(EntityType.MALE_FOX, maleFox);
        foxCub.setParents(EntityType.FEMALE_FOX, femaleFox);

        map.setEntity(nextMove, foxCub);
    }
}


