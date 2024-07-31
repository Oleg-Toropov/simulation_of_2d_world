package com.toropov.oleg.map;

import com.toropov.oleg.entity.*;
import com.toropov.oleg.entity.herbivore.Hen;
import com.toropov.oleg.entity.herbivore.Rooster;
import com.toropov.oleg.entity.predator.FemaleFox;
import com.toropov.oleg.entity.predator.MaleFox;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {

    @Test
    void testSetAndGetEntity() {
        WorldMap map = new WorldMap(20);
        Coordinates coordinates = new Coordinates(5, 5);
        Entity entity = new Grass(coordinates);

        map.setEntity(coordinates, entity);
        assertEquals(entity, map.getEntity(coordinates));
    }

    @Test
    void testRemoveEntity() {
        WorldMap map = new WorldMap(20);
        Coordinates coordinates = new Coordinates(5, 5);
        Entity entity = new Grass(coordinates);

        map.setEntity(coordinates, entity);
        map.removeEntity(coordinates);
        assertNull(map.getEntity(coordinates));
    }

    @Test
    void testIsSquareEmpty() {
        WorldMap map = new WorldMap(20);
        Coordinates coordinates = new Coordinates(5, 5);

        assertTrue(map.isSquareEmpty(coordinates));

        Entity entity = new Grass(coordinates);
        map.setEntity(coordinates, entity);
        assertFalse(map.isSquareEmpty(coordinates));
    }

    @Test
    void testIsWithinBounds() {
        WorldMap map = new WorldMap(20);
        Coordinates inBounds = new Coordinates(5, 5);
        Coordinates outOfBounds = new Coordinates(20, 20);

        assertTrue(map.isWithinBounds(inBounds));
        assertFalse(map.isWithinBounds(outOfBounds));
    }

    @Test
    void testCountEntities() {
        WorldMap map = new WorldMap(20);
        map.setEntity(new Coordinates(5, 5), new Grass(new Coordinates(5, 5)));
        map.setEntity(new Coordinates(6, 6), new Hen(new Coordinates(6, 6), 1, 20, 1));
        map.setEntity(new Coordinates(7, 7), new Rooster(new Coordinates(7, 7), 1, 20, 1));
        map.setEntity(new Coordinates(8, 8), new MaleFox(new Coordinates(8, 8), 1, 20, 1));
        map.setEntity(new Coordinates(9, 9), new FemaleFox(new Coordinates(9, 9), 1, 20, 1));

        Map<EntityType, Integer> count = map.countEntities();

        assertEquals(1, (int) count.get(EntityType.GRASS));
        assertEquals(1, (int) count.get(EntityType.HEN));
        assertEquals(1, (int) count.get(EntityType.ROOSTER));
        assertEquals(1, (int) count.get(EntityType.MALE_FOX));
        assertEquals(1, (int) count.get(EntityType.FEMALE_FOX));
    }
}
