package com.toropov.oleg.entity.herbivore;

import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.entity.EntityFactory;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;
import com.toropov.oleg.pathfinding.PathFinder;

import java.util.*;

/**
 * Represents chickens in the simulation.
 * Chickens can move towards grass or find a mate to reproduce.
 */
public abstract class Chickens extends Herbivore {
    private static final int ADDITIONAL_HEALTH = 10;

    /**
     * Constructs a Chicken with the specified coordinates, speed, health points, and generation.
     *
     * @param coordinates the coordinates of the chicken
     * @param speed the speed of the chicken
     * @param healthPoints the health points of the chicken
     * @param generation the generation of the chicken
     */
    public Chickens(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }


    /**
     * Checks if the number of hens and roosters exceeds the threshold.
     *
     * @param countEntities a map of entity counts
     * @return true if the number of hens and roosters exceeds the threshold, false otherwise
     */
    protected boolean areEntitiesExceedingThreshold(Map<EntityType, Integer> countEntities) {
        return countEntities.get(EntityType.HEN) > EntityFactory.HEN_COUNT * 2 &&
                countEntities.get(EntityType.ROOSTER) > EntityFactory.ROOSTER_COUNT * 2;
    }

    /**
     * Makes a move for the chicken. If the chicken is set to skip the next move, it regenerates health.
     * Otherwise, it finds the nearest grass or mate and moves towards it.
     *
     * @param map the world map
     */
    @Override
    public void makeMove(WorldMap map) {
        if (isSkipNextMove()) {
            regenerateHealth(ADDITIONAL_HEALTH);
            setSkipNextMove(false);
        } else {
            Coordinates target = findNearestGrassOrCouple(map);
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
     * Handles the movement of the chicken to the next position.
     * If the next position contains a hen or rooster, it checks if creating a chick is necessary.
     * Otherwise, it moves to the next position.
     *
     * @param map the world map
     * @param nextMove the coordinates of the next move
     * @param target the target coordinates
     */
    private void handleMove(WorldMap map, Coordinates nextMove, Coordinates target) {
        if (map.getEntity(nextMove) instanceof Hen || map.getEntity(nextMove) instanceof Rooster) {
            checkForCreateChick(map, nextMove);
        } else {
            move(nextMove, map);
        }

        if (nextMove.equals(target)) {
            setSkipNextMove(true);
        }
    }

    /**
     * Checks if a chick can be created at the specified position and creates it if possible.
     *
     * @param map the world map
     * @param nextMove the coordinates of the next move
     */
    protected void checkForCreateChick(WorldMap map, Coordinates nextMove) {
        Entity entityNextMove = map.getEntity(nextMove);
        Entity entityCurrentPos = map.getEntity(getCoordinates());

        if (entityNextMove instanceof Hen hen && entityCurrentPos instanceof Rooster rooster) {
            createChick(map, nextMove, hen, rooster);
        } else if (entityNextMove instanceof Rooster rooster && entityCurrentPos instanceof Hen hen) {
            createChick(map, nextMove, hen, rooster);
        }
    }

    /**
     * Creates a chick at the specified position.
     *
     * @param map the world map
     * @param nextMove the coordinates of the next move
     * @param hen the hen involved in creating the chick
     * @param rooster the rooster involved in creating the chick
     */
    private void createChick(WorldMap map, Coordinates nextMove, Hen hen, Rooster rooster) {
        map.removeEntity(getCoordinates());
        map.removeEntity(nextMove);

        int generationChick = Math.max(rooster.getGeneration(), hen.getGeneration()) + 1;
        Chick chick = new Chick(nextMove, 0, 2, generationChick);

        chick.setParents(EntityType.ROOSTER, rooster);
        chick.setParents(EntityType.HEN, hen);

        map.setEntity(nextMove, chick);
    }
}




