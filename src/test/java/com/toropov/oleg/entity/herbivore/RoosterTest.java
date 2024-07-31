package com.toropov.oleg.entity.herbivore;

import com.toropov.oleg.entity.Grass;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.WorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoosterTest {

    @Test
    void testMove() {
        WorldMap map = new WorldMap(20);
        Coordinates start = new Coordinates(5, 5);
        Coordinates end = new Coordinates(6, 6);
        Rooster rooster = new Rooster(start, 1, 20, 1);

        map.setEntity(start, rooster);
        rooster.move(end, map);

        assertNull(map.getEntity(start));
        assertEquals(rooster, map.getEntity(end));
    }

    @Test
    void testDecreaseHealth() {
        Coordinates coordinates = new Coordinates(5, 5);
        Rooster rooster = new Rooster(coordinates, 1, 10, 1);

        rooster.decreaseHealth();
        assertEquals(9, rooster.getHealthPoints());
    }

    @Test
    void testIsSquareAvailableForMove() {
        WorldMap map = new WorldMap(20);
        Coordinates start = new Coordinates(5, 5);
        Coordinates empty = new Coordinates(6, 6);
        Coordinates grass = new Coordinates(7, 7);
        Rooster rooster = new Rooster(start, 1, 20, 1);

        map.setEntity(start, rooster);
        map.setEntity(grass, new Grass(grass));

        assertTrue(rooster.isSquareAvailableForMove(empty, map));
        assertTrue(rooster.isSquareAvailableForMove(grass, map));
        assertFalse(rooster.isSquareAvailableForMove(start, map));
    }
}
