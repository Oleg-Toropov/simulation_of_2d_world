package com.toropov.oleg.entity;

import com.toropov.oleg.map.Coordinates;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EntityFactoryTest {

    @Test
    void testCreateAllEntitiesForMap() {
        EntityFactory factory = new EntityFactory();
        Map<Coordinates, Entity> entities = factory.createAllEntitiesForMap();

        assertNotNull(entities);
        assertTrue(entities.size() > 0);
    }
}
