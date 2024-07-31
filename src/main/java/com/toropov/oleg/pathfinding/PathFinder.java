package com.toropov.oleg.pathfinding;

import com.toropov.oleg.entity.Creature;
import com.toropov.oleg.map.Coordinates;
import com.toropov.oleg.map.WorldMap;

import java.util.*;
import java.util.logging.Logger;

/**
 * The PathFinder class provides methods for finding paths on the WorldMap using the A* algorithm.
 */
public class PathFinder {
    private static final Logger LOGGER = Logger.getLogger(PathFinder.class.getName());

    /**
     * Default constructor.
     * This class is not intended to be instantiated.
     */
    public PathFinder() {
        // Utility class, no instances allowed.
    }

    /**
     * Returns the set of possible moves for a creature.
     *
     * @return a set of coordinates representing possible moves.
     */
    private static Set<Coordinates> getCreatureMoves() {
        return Set.of(
                new Coordinates(-1, -1), new Coordinates(-1, 0), new Coordinates(-1, 1),
                new Coordinates(0, -1), new Coordinates(0, 1), new Coordinates(1, -1),
                new Coordinates(1, 0), new Coordinates(1, 1)
        );
    }

    /**
     * Finds the shortest path from start to goal using the A* algorithm.
     *
     * @param map the world map.
     * @param start the starting coordinates.
     * @param goal the goal coordinates.
     * @param creature the creature for which the path is being found.
     * @return a list of coordinates representing the path from start to goal.
     */
    public static List<Coordinates> findPathAStar(WorldMap map, Coordinates start, Coordinates goal, Creature creature) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        Map<Coordinates, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, heuristic(start, goal));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        int maxIterations = map.getMapSize() * map.getMapSize();

        LOGGER.info("Starting A* pathfinding from " + start + " to " + goal);

        return performAStarSearch(map, openSet, allNodes, goal, creature, maxIterations);
    }

    /**
     * Performs the A* search algorithm.
     *
     * @param map the world map.
     * @param openSet the priority queue of nodes to be evaluated.
     * @param allNodes the map of all nodes.
     * @param goal the goal coordinates.
     * @param creature the creature for which the path is being found.
     * @param maxIterations the maximum number of iterations allowed.
     * @return a list of coordinates representing the path from start to goal.
     */
    private static List<Coordinates> performAStarSearch(WorldMap map, PriorityQueue<Node> openSet,
                                                        Map<Coordinates, Node> allNodes, Coordinates goal,
                                                        Creature creature, int maxIterations) {
        int iterationCount = 0;

        while (!openSet.isEmpty()) {
            iterationCount++;
            if (iterationCount > maxIterations) {
                LOGGER.fine("Exceeded maximum iterations");
                break;
            }

            Node current = openSet.poll();

            if (current.coordinates.equals(goal)) {
                LOGGER.info("Path found to goal: " + goal);
                return reconstructPath(current);
            }

            processNeighborNodes(map, current, openSet, allNodes, goal, creature);
        }

        LOGGER.fine("No path found to goal: " + goal);
        return Collections.emptyList();
    }

    /**
     * Processes neighbor nodes during the A* search.
     *
     * @param map the world map.
     * @param current the current node being evaluated.
     * @param openSet the priority queue of nodes to be evaluated.
     * @param allNodes the map of all nodes.
     * @param goal the goal coordinates.
     * @param creature the creature for which the path is being found.
     */
    private static void processNeighborNodes(WorldMap map, Node current, PriorityQueue<Node> openSet,
                                             Map<Coordinates, Node> allNodes, Coordinates goal, Creature creature) {
        for (Coordinates shift : getCreatureMoves()) {
            Coordinates neighborCoords = current.coordinates.shift(shift);

            if (!creature.isSquareAvailableForMove(neighborCoords, map)) {
                continue;
            }

            int tentativeG = current.g + 1;
            Node neighbor = allNodes.getOrDefault(neighborCoords, new Node(neighborCoords));

            if (tentativeG < neighbor.g) {
                neighbor.g = tentativeG;
                neighbor.f = neighbor.g + heuristic(neighborCoords, goal);
                neighbor.parent = current;
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                }
                allNodes.put(neighborCoords, neighbor);
            }
        }
    }

    /**
     * Calculates the heuristic value between two coordinates.
     *
     * @param a the first coordinates.
     * @param b the second coordinates.
     * @return the heuristic value.
     */
    private static int heuristic(Coordinates a, Coordinates b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private static List<Coordinates> reconstructPath(Node node) {
        List<Coordinates> path = new LinkedList<>();
        while (node != null) {
            if (node.g != 0) {
                path.add(0, node.coordinates);
            }
            node = node.parent;
        }

        if (path.isEmpty()) {
            LOGGER.fine("Reconstructed path is empty");
        }

        return path;
    }

    /**
     * Represents a node in the A* search algorithm.
     */
    private static class Node {
        Coordinates coordinates;
        Node parent;
        int g;
        int f;

        /**
         * Constructs a Node with specified coordinates.
         *
         * @param coordinates the coordinates of the node.
         */
        Node(Coordinates coordinates) {
            this.coordinates = coordinates;
            this.g = Integer.MAX_VALUE;
            this.f = Integer.MAX_VALUE;
        }

        /**
         * Constructs a Node with specified coordinates, parent node, g value, and f value.
         *
         * @param coordinates the coordinates of the node.
         * @param parent the parent node.
         * @param g the g value (cost from start to this node).
         * @param f the f value (estimated cost from start to goal through this node).
         */
        Node(Coordinates coordinates, Node parent, int g, int f) {
            this.coordinates = coordinates;
            this.parent = parent;
            this.g = g;
            this.f = f;
        }
    }
}
