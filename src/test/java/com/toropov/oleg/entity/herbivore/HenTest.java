package com.toropov.oleg.entity.herbivore;

import com.toropov.oleg.entity.Grass;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.WorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HenTest {

    @Test
    void testMove() {
        WorldMap map = new WorldMap(20);
        Coordinates start = new Coordinates(5, 5);
        Coordinates end = new Coordinates(6, 6);
        Hen hen = new Hen(start, 1, 20, 1);

        map.setEntity(start, hen);
        hen.move(end, map);

        assertNull(map.getEntity(start));
        assertEquals(hen, map.getEntity(end));
    }

    @Test
    void testDecreaseHealth() {
        Coordinates coordinates = new Coordinates(5, 5);
        Hen hen = new Hen(coordinates, 1, 10, 1);

        hen.decreaseHealth();
        assertEquals(9, hen.getHealthPoints());
    }

    @Test
    void testIsSquareAvailableForMove() {
        WorldMap map = new WorldMap(20);
        Coordinates start = new Coordinates(5, 5);
        Coordinates empty = new Coordinates(6, 6);
        Coordinates grass = new Coordinates(7, 7);
        Hen hen = new Hen(start, 1, 20, 1);

        map.setEntity(start, hen);
        map.setEntity(grass, new Grass(grass));

        assertTrue(hen.isSquareAvailableForMove(empty, map));
        assertTrue(hen.isSquareAvailableForMove(grass, map));
        assertFalse(hen.isSquareAvailableForMove(start, map));
    }
}
