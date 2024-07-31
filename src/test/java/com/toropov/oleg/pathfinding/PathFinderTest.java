package com.toropov.oleg.pathfinding;

import com.toropov.oleg.entity.Grass;
import com.toropov.oleg.entity.Rock;
import com.toropov.oleg.entity.Tree;
import com.toropov.oleg.entity.herbivore.Hen;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.WorldMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PathFinderTest {
    private WorldMap map;
    private Grass grass;
    private Hen hen;

    @BeforeEach
    public void setUp() {
        map = new WorldMap(20);
        grass = new Grass(new Coordinates(0, 0));
        hen = new Hen(new Coordinates(0, 0), 1, 10, 1);
    }

    @Test
    public void testFindPathAStar_PathExists() {
        Coordinates start = new Coordinates(0, 0);
        Coordinates goal = new Coordinates(2, 2);

        map.setEntity(start, hen);
        map.setEntity(goal, grass);

        List<Coordinates> path = PathFinder.findPathAStar(map, start, goal, hen);

        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");
        assertEquals(goal, path.get(path.size() - 1), "Path should end at the goal");
    }

    @Test
    public void testFindPathAStar_NoPath() {
        Coordinates start = new Coordinates(0, 0);
        Coordinates goal = new Coordinates(2, 2);

        map.setEntity(start, hen);
        // Surround the start point with obstacles to make it unreachable
        map.setEntity(new Coordinates(0, 1), new Rock(new Coordinates(0, 1)));
        map.setEntity(new Coordinates(1, 0), new Tree(new Coordinates(1, 0)));
        map.setEntity(new Coordinates(1, 1), new Rock(new Coordinates(1, 1)));

        // Ensure obstacles are set
        assertTrue(map.getEntity(new Coordinates(0, 1)) instanceof Rock, "Obstacle should be a Rock");
        assertTrue(map.getEntity(new Coordinates(1, 0)) instanceof Tree, "Obstacle should be a Tree");
        assertTrue(map.getEntity(new Coordinates(1, 1)) instanceof Rock, "Obstacle should be a Rock");

        List<Coordinates> path = PathFinder.findPathAStar(map, start, goal, hen);

        assertNotNull(path, "Path should not be null");
        assertTrue(path.isEmpty(), "Path should be empty as there is no possible way");
    }

    @Test
    public void testFindPathAStar_SameStartAndGoal() {
        Coordinates startAndGoal = new Coordinates(0, 0);

        map.setEntity(startAndGoal, hen);

        List<Coordinates> path = PathFinder.findPathAStar(map, startAndGoal, startAndGoal, hen);

        assertNotNull(path, "Path should not be null");
        assertTrue(path.isEmpty(), "Path should be empty when start and goal are the same");
    }
}
