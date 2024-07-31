package com.toropov.oleg.entity.predator;

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
 * Represents a fox cub in the simulation.
 * Fox cubs have parents and eventually transform into adult foxes.
 */
public class FoxCub extends Foxes implements Kids {
    private final Map<EntityType, Entity> parents = new HashMap<>();

    /**
     * Constructs a FoxCub with specified coordinates, speed, health points, and generation.
     *
     * @param coordinates the initial coordinates of the fox cub
     * @param speed the movement speed of the fox cub
     * @param healthPoints the initial health points of the fox cub
     * @param generation the generation of the fox cub
     */
    public FoxCub(Coordinates coordinates, int speed, int healthPoints, int generation) {
        super(coordinates, speed, healthPoints, generation);
    }

    /**
     * Sets a parent entity for the fox cub.
     *
     * @param type the type of the parent entity
     * @param entity the parent entity
     */
    public void setParents(EntityType type, Entity entity) {
        parents.put(type, entity);
    }

    /**
     * Gets a list of all parent entities of the fox cub.
     *
     * @return a list of parent entities
     */
    public List<Entity> getAllParents() {
        return new ArrayList<>(parents.values());
    }

    /**
     * Gets a parent entity based on the fox cub's health points and current entity counts.
     *
     * @param map the world map
     * @param healthPoints the current health points of the fox cub
     * @return the selected parent entity
     */
    public Entity getParents(WorldMap map, int healthPoints) {
        Map<EntityType, Integer> countEntities = map.countEntities();
        if (healthPoints == 1) {
            return getParentByPriority(countEntities, parents, EntityType.MALE_FOX, EntityType.FEMALE_FOX);
        } else {
            return getParentByPresence(parents, EntityType.MALE_FOX, EntityType.FEMALE_FOX);
        }
    }

    /**
     * Makes a move for the fox cub. If the fox cub still has parents, it waits for them to leave the current cell.
     * Otherwise, it transforms into an adult.
     *
     * @param map the world map
     */
    @Override
    public void makeMove(WorldMap map) {
        if (!parents.isEmpty()) {
            decreaseHealth();
            Coordinates target = findNearestHerbivoreOrCouple(map);
            moveParentOfChild(map, getCoordinates(), target, this, getParents(map, getHealthPoints()));
        } else {
            transformIntoAdult(map);
        }
    }

    /**
     * Transforms the fox cub into an adult fox of a specific gender based on the current entity counts.
     *
     * @param map the world map
     */
    private void transformIntoAdult(WorldMap map) {
        map.removeEntity(getCoordinates());
        Map<EntityType, Integer> countEntities = map.countEntities();
        Entity newEntity = (countEntities.get(EntityType.MALE_FOX) < countEntities.get(EntityType.FEMALE_FOX)) ?
                new MaleFox(getCoordinates(), 1, 20, getGeneration()) :
                new FemaleFox(getCoordinates(), 1, 20, getGeneration());

        map.setEntity(getCoordinates(), newEntity);
    }
}
