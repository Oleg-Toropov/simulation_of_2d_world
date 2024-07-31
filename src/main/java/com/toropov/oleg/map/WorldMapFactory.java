package com.toropov.oleg.map;

import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.entity.EntityFactory;

import java.util.Map;

/**
 * Factory class to create and populate a WorldMap with entities.
 */
public class WorldMapFactory {
    private static final int MAP_SIZE = 20;
    private final EntityFactory entityFactory = new EntityFactory();

    /**
     * Default constructor.
     * This class is not intended to be instantiated.
     */
    public WorldMapFactory() {
        // Utility class, no instances allowed.
    }

    /**
     * Creates a WorldMap and populates it with entities.
     *
     * @return a populated WorldMap
     */
    public WorldMap creatMap() {
        WorldMap map = new WorldMap(MAP_SIZE);
        Map<Coordinates, Entity> createdEntities = entityFactory.createAllEntitiesForMap();

        populateMapWithEntities(map, createdEntities);

        return map;
    }

    /**
     * Populates the provided WorldMap with the given entities.
     *
     * @param map the WorldMap to be populated
     * @param createdEntities the entities to populate the map with
     */
    private void populateMapWithEntities(WorldMap map, Map<Coordinates, Entity> createdEntities) {
        for (Coordinates coordinates : createdEntities.keySet()) {
            map.setEntity(coordinates, createdEntities.get(coordinates));
        }
    }
}
