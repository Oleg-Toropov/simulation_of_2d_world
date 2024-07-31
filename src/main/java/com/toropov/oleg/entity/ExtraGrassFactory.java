package com.toropov.oleg.entity;

import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.WorldMap;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Factory class for adding extra grass to the world map when needed.
 */
public class ExtraGrassFactory {
    private static final int AMOUNT_OF_ADDITIONAL_GRASS = EntityFactory.GRASS_COUNT - EntityFactory.GRASS_COUNT / 3;
    private static final int MAP_SIZE = 20;
    private static boolean isGrassOver = false;

    /**
     * Default constructor.
     * This class is not intended to be instantiated.
     */
    public ExtraGrassFactory() {
        // Utility class, no instances allowed.
    }

    /**
     * Sets the flag indicating if the grass is over.
     *
     * @param isGrassOver true if the grass is over, false otherwise
     */
    public static void setIsGrassOver(boolean isGrassOver) {
        ExtraGrassFactory.isGrassOver = isGrassOver;
    }

    /**
     * Checks if the grass is over in the simulation.
     * This method returns the value of the isGrassOver flag,
     * which indicates whether additional grass needs to be added to the map.
     *
     * @return true if the grass is over and additional grass should be added, false otherwise
     */
    public static boolean isGrassOver() {
        return isGrassOver;
    }

    /**
     * Adds additional grass to the world map if the grass is over.
     *
     * @param map the world map
     */
    public static void addingGrass(WorldMap map) {
        if (isGrassOver) {
            Set<Coordinates> randomCoordinates = createRandomCoordinates(map);
            for (Coordinates coordinates : randomCoordinates) {
                map.setEntity(coordinates, new Grass(coordinates));
            }

            isGrassOver = false;
        }
    }

    /**
     * Creates a set of random coordinates within the map where the grass can be added.
     *
     * @param map the world map
     * @return a set of random coordinates
     */
    private static Set<Coordinates> createRandomCoordinates(WorldMap map) {
        Random random = new Random();
        Set<Coordinates> randomCoordinates = new HashSet<>();

        int count = AMOUNT_OF_ADDITIONAL_GRASS;
        while (count > 0) {
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);
            Coordinates currentRandomCoordinates = new Coordinates(x, y);

            if (map.isSquareEmpty(currentRandomCoordinates)) {
                randomCoordinates.add(currentRandomCoordinates);
                count--;
            }
        }

        return randomCoordinates;
    }
}
