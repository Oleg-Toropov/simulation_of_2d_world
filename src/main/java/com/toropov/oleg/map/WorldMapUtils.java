package com.toropov.oleg.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class for operations related to the WorldMap.
 */
public class WorldMapUtils {

    /**
     * Default constructor.
     * This class is not intended to be instantiated.
     */
    public WorldMapUtils() {
        // Utility class, no instances allowed.
    }

    /**
     * Generates a list of coordinate shifts sorted by their Manhattan distance from the origin.
     *
     * @param mapSize the size of the map
     * @return a list of coordinate shifts sorted by Manhattan distance
     */
    public static List<int[]> getAllSortedShiftsForCoordinates(int mapSize) {
        List<int[]> shifts = new ArrayList<>();

        for (int radius = 1; radius < mapSize; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    shifts.add(new int[]{dx, dy});
                }
            }
        }

        shifts.sort(Comparator.comparingInt(shift -> calculateManhattanDistance(shift[0], shift[1])));

        return shifts;
    }

    /**
     * Calculates the Manhattan distance between two points.
     *
     * @param i the x-coordinate of the first point
     * @param j the y-coordinate of the first point
     * @return the Manhattan distance between the two points
     */
    private static int calculateManhattanDistance(int i, int j) {
        return Math.abs(i) + Math.abs(j);
    }
}
