package com.toropov.oleg.entity.predator;

import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FoxCubTest {

    @Test
    void testMakeMove() {
        WorldMap map = new WorldMap(20);
        Coordinates start = new Coordinates(5, 5);
        FoxCub foxCub = new FoxCub(start, 1, 2, 1);

        Entity maleFox = new MaleFox(new Coordinates(6, 6), 1, 20, 1);
        Entity femaleFox = new FemaleFox(new Coordinates(7, 7), 1, 20, 1);

        foxCub.setParents(EntityType.MALE_FOX, maleFox);
        foxCub.setParents(EntityType.FEMALE_FOX, femaleFox);

        map.setEntity(start, foxCub);
        map.setEntity(maleFox.getCoordinates(), maleFox);
        map.setEntity(femaleFox.getCoordinates(), femaleFox);

        foxCub.makeMove(map);

        assertNotNull(map.getEntity(start));
    }

    @Test
    void testTransformIntoAdult() {
        WorldMap map = new WorldMap(20);
        Coordinates start = new Coordinates(5, 5);
        FoxCub foxCub = new FoxCub(start, 1, 0, 1); // Make health 0 to force transformation

        map.setEntity(start, foxCub);

        foxCub.makeMove(map);

        Entity adultFox = map.getEntity(start);
        assertTrue(adultFox instanceof MaleFox || adultFox instanceof FemaleFox);
    }

    @Test
    void testGetParents() {
        WorldMap map = new WorldMap(20);
        Coordinates start = new Coordinates(5, 5);
        FoxCub foxCub = new FoxCub(start, 1, 2, 1);

        Entity maleFox = new MaleFox(new Coordinates(6, 6), 1, 20, 1);
        Entity femaleFox = new FemaleFox(new Coordinates(7, 7), 1, 20, 1);

        foxCub.setParents(EntityType.MALE_FOX, maleFox);
        foxCub.setParents(EntityType.FEMALE_FOX, femaleFox);

        map.setEntity(start, foxCub);
        map.setEntity(maleFox.getCoordinates(), maleFox);
        map.setEntity(femaleFox.getCoordinates(), femaleFox);

        Map<EntityType, Integer> countEntities = map.countEntities();

        // Ensure parents are correctly retrieved
        assertEquals(maleFox, foxCub.getParents(map, 2));
        assertEquals(femaleFox, foxCub.getParents(map, 2));
    }
}
