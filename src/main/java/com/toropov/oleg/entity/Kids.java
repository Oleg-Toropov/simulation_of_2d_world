package com.toropov.oleg.entity;

import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;
import com.toropov.oleg.pathfinding.PathFinder;

import java.util.List;
import java.util.Map;

/**
 * Interface for managing parent-related behaviors for entities.
 */
public interface Kids {

    /**
     * Retrieves a parent entity based on the priority of their type.
     *
     * @param countEntities  a map of entity type counts
     * @param parents        a map of parent entities
     * @param primaryType    the primary entity type to check
     * @param secondaryType  the secondary entity type to check
     * @return the parent entity of the higher priority type
     */
    default Entity getParentByPriority(Map<EntityType, Integer> countEntities, Map<EntityType, Entity> parents, EntityType primaryType, EntityType secondaryType) {
        return (countEntities.get(primaryType) < countEntities.get(secondaryType)) ? parents.remove(primaryType) :
                parents.remove(secondaryType);
    }

    /**
     * Retrieves a parent entity based on their presence in the map.
     *
     * @param parents        a map of parent entities
     * @param primaryType    the primary entity type to check
     * @param secondaryType  the secondary entity type to check
     * @return the parent entity if present
     */
    default Entity getParentByPresence(Map<EntityType, Entity> parents, EntityType primaryType, EntityType secondaryType) {
        return parents.containsKey(primaryType) ? parents.remove(primaryType) : parents.remove(secondaryType);
    }

    /**
     * Moves the parent entity of a child entity to a target location.
     *
     * @param map          the world map
     * @param current      the current coordinates of the child entity
     * @param target       the target coordinates
     * @param kids         the child entity
     * @param parentEntity the parent entity to be moved
     */
    default void moveParentOfChild(WorldMap map, Coordinates current, Coordinates target, Creature kids, Entity parentEntity) {
        if (target != null) {
            List<Coordinates> path = PathFinder.findPathAStar(map, current, target, kids);

            if (!path.isEmpty()) {
                Coordinates nextMove = path.get(0);
                if (parentEntity instanceof Creature creature) {
                    map.setEntity(nextMove, creature);

                    if (nextMove.equals(target)) {
                        creature.setSkipNextMove(true);
                    }
                }
            }
        }
    }
}
