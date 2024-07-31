package com.toropov.oleg.map;

import com.toropov.oleg.entity.Creature;
import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.entity.Grass;
import com.toropov.oleg.entity.herbivore.Chick;
import com.toropov.oleg.entity.predator.FemaleFox;
import com.toropov.oleg.entity.predator.FoxCub;
import com.toropov.oleg.entity.predator.MaleFox;
import com.toropov.oleg.entity.herbivore.Hen;
import com.toropov.oleg.entity.herbivore.Rooster;

import java.util.*;

/**
 * The WorldMap class represents the map where entities are placed and interact.
 */
public class WorldMap {
    private final int mapSize;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    /**
     * Constructs a WorldMap with the specified size.
     *
     * @param mapSize the size of the map
     */
    public WorldMap(int mapSize) {
        this.mapSize = mapSize;
    }

    /**
     * Gets the size of the map.
     *
     * @return the size of the map
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Sets an entity at the specified coordinates.
     *
     * @param coordinates the coordinates where the entity is to be placed
     * @param entity the entity to be placed
     */
    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    /**
     * Gets the entity at the specified coordinates.
     *
     * @param coordinates the coordinates to retrieve the entity from
     * @return the entity at the specified coordinates, or null if no entity is present
     */
    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    /**
     * Gets a list of all creatures on the map.
     *
     * @return a list of all creatures
     */
    public List<Creature> getAllCreatures() {
        List<Creature> creatures = new ArrayList<>();
        for (Entity entity : entities.values()) {
            if (entity instanceof Creature) {
                creatures.add((Creature) entity);
            }
        }
        return creatures;
    }

    /**
     * Removes the entity at the specified coordinates.
     *
     * @param coordinates the coordinates to remove the entity from
     */
    public void removeEntity(Coordinates coordinates) {
        entities.remove(coordinates);
    }

    /**
     * Checks if the specified coordinates are empty (i.e., no entity is present).
     *
     * @param coordinates the coordinates to check
     * @return true if the coordinates are empty, false otherwise
     */
    public boolean isSquareEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    /**
     * Checks if the specified coordinates are within the bounds of the map.
     *
     * @param coordinates the coordinates to check
     * @return true if the coordinates are within bounds, false otherwise
     */
    public boolean isWithinBounds(Coordinates coordinates) {
        return coordinates.getX() >= 0 && coordinates.getX() < mapSize &&
                coordinates.getY() >= 0 && coordinates.getY() < mapSize;
    }

    /**
     * Counts the entities of each type on the map.
     *
     * @return a map with the count of each entity type
     */
    public Map<EntityType, Integer> countEntities() {
        Map<EntityType, Integer> countEntities = initializeCountMap();

        for (Entity entity : entities.values()) {
            updateEntityCount(countEntities, entity);
            if (entity instanceof FoxCub) {
                updateParentCount(countEntities, ((FoxCub) entity).getAllParents());
            } else if (entity instanceof Chick) {
                updateParentCount(countEntities, ((Chick) entity).getAllParents());
            }
        }

        countEntities.put(EntityType.ALL_CHICKEN, countEntities.get(EntityType.ROOSTER) +
                countEntities.get(EntityType.HEN) + countEntities.get(EntityType.CHICK));
        countEntities.put(EntityType.ALL_FOXES, countEntities.get(EntityType.MALE_FOX) +
                countEntities.get(EntityType.FEMALE_FOX) + countEntities.get(EntityType.FOX_CUB));

        return countEntities;
    }

    /**
     * Initializes a map with all entity types set to a count of zero.
     *
     * @return the initialized map
     */
    private Map<EntityType, Integer> initializeCountMap() {
        Map<EntityType, Integer> countEntities = new EnumMap<>(EntityType.class);
        for (EntityType type : EntityType.values()) {
            countEntities.put(type, 0);
        }
        return countEntities;
    }

    /**
     * Updates the entity count for a given entity.
     *
     * @param countEntities the map of entity counts
     * @param entity the entity to update the count for
     */
    private void updateEntityCount(Map<EntityType, Integer> countEntities, Entity entity) {
        if (entity instanceof Rooster) {
            countEntities.merge(EntityType.ROOSTER, 1, Integer::sum);
        } else if (entity instanceof Hen) {
            countEntities.merge(EntityType.HEN, 1, Integer::sum);
        } else if (entity instanceof Grass) {
            countEntities.merge(EntityType.GRASS, 1, Integer::sum);
        } else if (entity instanceof MaleFox) {
            countEntities.merge(EntityType.MALE_FOX, 1, Integer::sum);
        } else if (entity instanceof FemaleFox) {
            countEntities.merge(EntityType.FEMALE_FOX, 1, Integer::sum);
        } else if (entity instanceof FoxCub) {
            countEntities.merge(EntityType.FOX_CUB, 1, Integer::sum);
        } else if (entity instanceof Chick) {
            countEntities.merge(EntityType.CHICK, 1, Integer::sum);
        }
    }

    /**
     * Updates the parent count for a given list of parents.
     *
     * @param countEntities the map of entity counts
     * @param parents the list of parents to update the count for
     */
    private void updateParentCount(Map<EntityType, Integer> countEntities, List<Entity> parents) {
        for (Entity parent : parents) {
            if (parent instanceof FemaleFox) {
                countEntities.merge(EntityType.FEMALE_FOX, 1, Integer::sum);
            } else if (parent instanceof MaleFox) {
                countEntities.merge(EntityType.MALE_FOX, 1, Integer::sum);
            } else if (parent instanceof Hen) {
                countEntities.merge(EntityType.HEN, 1, Integer::sum);
            } else if (parent instanceof Rooster) {
                countEntities.merge(EntityType.ROOSTER, 1, Integer::sum);
            }
        }
    }
}
