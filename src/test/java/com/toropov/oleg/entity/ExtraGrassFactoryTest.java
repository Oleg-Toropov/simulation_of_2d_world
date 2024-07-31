package com.toropov.oleg.entity;

import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraGrassFactoryTest {

    @Test
    void testSetIsGrassOver() {
        // Initially, isGrassOver should be false
        ExtraGrassFactory.setIsGrassOver(false);
        assertFalse(ExtraGrassFactory.isGrassOver());

        // Set isGrassOver to true and verify
        ExtraGrassFactory.setIsGrassOver(true);
        assertTrue(ExtraGrassFactory.isGrassOver());
    }

    @Test
    void testAddingGrassWhenGrassIsOver() {
        WorldMap map = new WorldMap(20);
        ExtraGrassFactory.setIsGrassOver(true);

        // Count the initial number of grass entities
        int initialGrassCount = map.countEntities().getOrDefault(EntityType.GRASS, 0);

        // Add grass to the map
        ExtraGrassFactory.addingGrass(map);

        // Count the grass entities after adding
        int finalGrassCount = map.countEntities().getOrDefault(EntityType.GRASS, 0);

        // Verify that grass was added to the map
        assertTrue(finalGrassCount > initialGrassCount);
    }

    @Test
    void testAddingGrassWhenGrassIsNotOver() {
        WorldMap map = new WorldMap(20);
        ExtraGrassFactory.setIsGrassOver(false);

        // Count the initial number of grass entities
        int initialGrassCount = map.countEntities().getOrDefault(EntityType.GRASS, 0);

        // Attempt to add grass to the map
        ExtraGrassFactory.addingGrass(map);

        // Count the grass entities after attempting to add
        int finalGrassCount = map.countEntities().getOrDefault(EntityType.GRASS, 0);

        // Verify that no grass was added to the map
        assertEquals(initialGrassCount, finalGrassCount);
    }
}
