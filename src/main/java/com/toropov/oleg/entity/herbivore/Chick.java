package com.toropov.oleg.entity.herbivore;

import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.entity.Kids;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a chick in the simulation.
 * Chicks have parents and eventually transform into adult chicken.
 */
public class Chick extends Chickens implements Kids {
    private final Map<EntityType, Entity> parents = new HashMap<>();

    /**
     * Constructs a Chick with the specified coordinates, speed, health points, and generation.
     *
     * @param coordinates the coordinates of the chick
     * @param speed the speed of the chick
     * @param healthPoints the health points of the chick
     * @param generation the generation of the chick
     */
    public Chick(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Sets the parents of the chick.
     *
     * @param type the type of the parent entity
     * @param entity the parent entity
     */
    public void setParents(EntityType type, Entity entity) {
        parents.put(type, entity);
    }

    /**
     * Gets all parents of the chick.
     *
     * @return a list of parent entities
     */
    public List<Entity> getAllParents() {
        return new ArrayList<>(parents.values());
    }

    /**
     * Gets the parent based on the chick's health points.
     *
     * @param map the world map
     * @param healthPoints the health points of the chick
     * @return the parent entity
     */
    public Entity getParents(WorldMap map, int healthPoints) {
        Map<EntityType, Integer> countEntities = map.countEntities();
        if (healthPoints == 1) {
            return getParentByPriority(countEntities, parents, EntityType.ROOSTER, EntityType.HEN);
        } else {
            return getParentByPresence(parents, EntityType.ROOSTER, EntityType.HEN);
        }
    }

    /**
     * Makes a move for the chick. If the chick still has parents, it waits for them to leave the current cell.
     * Otherwise, it transforms into an adult.
     *
     * @param map the world map
     */
    @Override
    public void makeMove(WorldMap map) {
        if (!parents.isEmpty()) {
            decreaseHealth();
            Coordinates target = findNearestGrassOrCouple(map);
            moveParentOfChild(map, getCoordinates(), target, this, getParents(map, getHealthPoints()));
        } else {
            transformIntoAdult(map);
        }
    }

    /**
     * Transforms the chick into an adult chicken of a specific gender based on the current entity counts.
     *
     * @param map the world map
     */
    private void transformIntoAdult(WorldMap map) {
        map.removeEntity(getCoordinates());
        Map<EntityType, Integer> countEntities = map.countEntities();
        Entity newEntity = (countEntities.get(EntityType.ROOSTER) < countEntities.get(EntityType.HEN)) ?
                new Rooster(getCoordinates(), 1, 20, getGeneration()) :
                new Hen(getCoordinates(), 1, 20, getGeneration());

        map.setEntity(getCoordinates(), newEntity);
    }
}
