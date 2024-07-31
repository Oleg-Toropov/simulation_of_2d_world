package com.toropov.oleg.entity;

import com.toropov.oleg.entity.herbivore.Hen;
import com.toropov.oleg.entity.herbivore.Rooster;
import com.toropov.oleg.entity.predator.FemaleFox;
import com.toropov.oleg.entity.predator.MaleFox;
import com.toropov.oleg.map.Coordinates;

import java.util.*;

/**
 * Factory class for creating entities and populating the world map.
 */
public class EntityFactory {

    /**
     * The number of male foxes in the simulation.
     */
    public static final int MALE_FOX_COUNT = 1;

    /**
     * The number of female foxes in the simulation.
     */
    public static final int FEMALE_FOX_COUNT = 1;

    /**
     * The number of hens in the simulation.
     */
    public static final int HEN_COUNT = 12;

    /**
     * The number of roosters in the simulation.
     */
    public static final int ROOSTER_COUNT = 12;

    /**
     * The number of grass entities in the simulation.
     */
    public static final int GRASS_COUNT = 60;

    /**
     * The number of rock entities in the simulation.
     */
    public static final int ROCK_COUNT = 20;

    /**
     * The number of tree entities in the simulation.
     */
    public static final int TREE_COUNT = 60;

    /**
     * The size of the map used in the simulation.
     */
    public static final int MAP_SIZE = 20;

    /**
     * Default constructor.
     * This class is not intended to be instantiated.
     */
    public EntityFactory() {
        // Utility class, no instances allowed.
    }

    /**
     * Creates all entities for the world map.
     *
     * @return a map of coordinates to entities
     */
    public Map<Coordinates, Entity> createAllEntitiesForMap() {
        List<Coordinates> randomCoordinates = createRandomCoordinates();
        Map<Coordinates, Entity> createdEntities = new HashMap<>();

        addEntities(createdEntities, randomCoordinates);

        return createdEntities;
    }

    /**
     * Generates a list of random coordinates within the map size.
     *
     * @return a list of random coordinates
     */
    private List<Coordinates> createRandomCoordinates() {
        Random random = new Random();
        Set<Coordinates> randomCoordinates = new HashSet<>();

        int count = MALE_FOX_COUNT + FEMALE_FOX_COUNT + ROOSTER_COUNT + HEN_COUNT + GRASS_COUNT + TREE_COUNT + ROCK_COUNT;
        while (randomCoordinates.size() < count) {
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);
            randomCoordinates.add(new Coordinates(x, y));
        }

        List<Coordinates> coordinatesList = new ArrayList<>(randomCoordinates);
        Collections.shuffle(coordinatesList);
        return coordinatesList;
    }

    /**
     * Adds entities to the created entities map based on the given coordinates.
     *
     * @param createdEntities the map of created entities
     * @param randomCoordinates the list of random coordinates
     */
    private void addEntities(Map<Coordinates, Entity> createdEntities, List<Coordinates> randomCoordinates) {
        int index = 0;

        index = addEntitiesOfType(createdEntities, randomCoordinates, index, MALE_FOX_COUNT, coordinates -> new MaleFox(coordinates, 1, 20, 1));
        index = addEntitiesOfType(createdEntities, randomCoordinates, index, FEMALE_FOX_COUNT, coordinates -> new FemaleFox(coordinates, 1, 20, 1));
        index = addEntitiesOfType(createdEntities, randomCoordinates, index, ROOSTER_COUNT, coordinates -> new Rooster(coordinates, 1, 20, 1));
        index = addEntitiesOfType(createdEntities, randomCoordinates, index, HEN_COUNT, coordinates -> new Hen(coordinates, 1, 20, 1));
        index = addEntitiesOfType(createdEntities, randomCoordinates, index, GRASS_COUNT, Grass::new);
        index = addEntitiesOfType(createdEntities, randomCoordinates, index, ROCK_COUNT, Rock::new);
        addEntitiesOfType(createdEntities, randomCoordinates, index, TREE_COUNT, Tree::new);
    }

    /**
     * Adds a specific type of entities to the created entities map.
     *
     * @param createdEntities the map of created entities
     * @param randomCoordinates the list of random coordinates
     * @param startIndex the starting index for adding entities
     * @param count the number of entities to add
     * @param creator the entity creator functional interface
     * @return the updated index after adding the entities
     */
    private int addEntitiesOfType(Map<Coordinates, Entity> createdEntities, List<Coordinates> randomCoordinates, int startIndex, int count, EntityCreator creator) {
        for (int i = 0; i < count; i++) {
            Coordinates coordinates = randomCoordinates.get(startIndex + i);
            createdEntities.put(coordinates, creator.create(coordinates));
        }
        return startIndex + count;
    }

    /**
     * Functional interface for creating entities.
     */
    @FunctionalInterface
    private interface EntityCreator {
        Entity create(Coordinates coordinates);
    }
}