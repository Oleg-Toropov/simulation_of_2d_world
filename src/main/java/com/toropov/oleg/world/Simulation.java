package com.toropov.oleg.world;

import com.toropov.oleg.entity.EntityFactory;
import com.toropov.oleg.entity.ExtraGrassFactory;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;
import com.toropov.oleg.map.WorldMapFactory;
import com.toropov.oleg.map.WorldMapPanel;

import javax.swing.*;
import java.util.Map;

/**
 * The Simulation class manages the entire lifecycle of the simulation,
 * including initializing the map, starting, pausing, and stopping the simulation,
 * and updating the UI.
 */
public class Simulation {
    private WorldMap map;
    private WorldMapPanel panel;
    private Actions actions;
    private SimulationUI ui;

    private int moveCounter = 0;

    /**
     * Default constructor.
     * This class is not intended to be instantiated.
     */
    public Simulation() {
        // Utility class, no instances allowed.
    }

    /**
     * Runs the simulation by initializing the map and UI, and updating the entity counts.
     */
    public void run() {
        initializeMap();
        initializeUI();
        updateEntityCounts();
    }


    /**
     * Initializes the map for the simulation.
     */
    private void initializeMap() {
        map = new WorldMapFactory().creatMap();
    }

    /**
     * Initializes the UI for the simulation.
     */
    private void initializeUI() {
        panel = new WorldMapPanel(map);
        ui = new SimulationUI(panel, this::startSimulation, this::pauseSimulation, this::stopSimulation);
    }

    /**
     * Starts the simulation.
     */
    private void startSimulation() {
        if (actions == null) {
            actions = new Actions(map, panel::repaint, this::updateEntityCounts, this);
            actions.start();
        } else if (actions.isPaused()) {
            actions.resume();
        }
    }

    /**
     * Pauses the simulation.
     */
    private void pauseSimulation() {
        if (actions != null) {
            actions.pause();
        }
    }

    /**
     * Stops the simulation and reinitializes the map.
     */
    private void stopSimulation() {
        if (actions != null) {
            actions.stop();
            actions = null;
            initializeMap();
            panel.setMap(map);
            panel.repaint();
        }
    }

    /**
     * Updates the entity counts in the simulation.
     */
    private void updateEntityCounts() {
        Map<EntityType, Integer> countEntities = map.countEntities();
        ExtraGrassFactory.setIsGrassOver(countEntities.get(EntityType.GRASS) <= EntityFactory.GRASS_COUNT / 3);
        ui.updateCounts(countEntities, moveCounter);
    }

    /**
     * Increments the move counter and updates the entity counts.
     */
    public void incrementMoveCounter() {
        moveCounter++;
        updateEntityCounts();
    }

    /**
     * Closes the simulation window.
     */
    public void closeWindow() {
        SwingUtilities.invokeLater(ui::dispose);
    }
}