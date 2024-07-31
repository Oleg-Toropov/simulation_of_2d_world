package com.toropov.oleg.world;

import com.toropov.oleg.entity.Creature;
import com.toropov.oleg.entity.ExtraGrassFactory;
import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Actions class manages the simulation actions, including starting, pausing, resuming,
 * and stopping the simulation. It also handles the movement of creatures and updates the
 * UI and entity counts.
 */
public class Actions {
    private static final Logger LOGGER = Logger.getLogger(Actions.class.getName());
    private static final int INITIAL_DELAY = 0;
    private static final int PERIOD = 1;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final int SHUTDOWN_TIMEOUT = 1;

    private final WorldMap map;
    private ScheduledExecutorService scheduler;
    private final Runnable updateUIRunnable;
    private final Runnable updateEntityCountsRunnable;
    private final AtomicBoolean isPaused = new AtomicBoolean(false);
    private final Simulation simulation;

    /**
     * Constructs an Actions object.
     *
     * @param map                      the WorldMap object representing the simulation map
     * @param updateUIRunnable         a Runnable to update the UI
     * @param updateEntityCountsRunnable a Runnable to update the entity counts
     * @param simulation               the Simulation object managing the simulation
     */
    public Actions(WorldMap map, Runnable updateUIRunnable, Runnable updateEntityCountsRunnable, Simulation simulation) {
        this.map = map;
        this.updateUIRunnable = updateUIRunnable;
        this.updateEntityCountsRunnable = updateEntityCountsRunnable;
        this.simulation = simulation;
        LOGGER.setLevel(Level.INFO);
    }

    /**
     * Starts the simulation.
     */
    public void start() {
        if (scheduler != null && !scheduler.isShutdown()) {
            return;
        }
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {

            try {
                if (!isPaused.get()) {
                    LOGGER.log(Level.INFO, "Simulation step started.");
                    allCreaturesMakeMove();
                    ExtraGrassFactory.addingGrass(map);
                    updateEntityCountsRunnable.run();
                    updateUIRunnable.run();
                    LOGGER.log(Level.INFO, "Simulation step completed.");
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error during simulation step", e);
            }
        }, INITIAL_DELAY, PERIOD, TIME_UNIT);
    }

    /**
     * Pauses the simulation.
     */
    public void pause() {
        isPaused.set(true);
        LOGGER.log(Level.INFO, "Simulation paused.");
    }

    /**
     * Resumes the simulation.
     */
    public void resume() {
        isPaused.set(false);
        LOGGER.log(Level.INFO, "Simulation resumed.");
    }

    /**
     * Checks if the simulation is paused.
     *
     * @return true if the simulation is paused, false otherwise
     */
    public boolean isPaused() {
        return isPaused.get();
    }

    /**
     * Stops the simulation.
     */
    public void stop() {
        if (scheduler != null) {
            isPaused.set(true);
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(SHUTDOWN_TIMEOUT, TIME_UNIT)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error during scheduler shutdown", e);
                scheduler.shutdownNow();
            } finally {
                scheduler = null;
                LOGGER.log(Level.INFO, "Simulation stopped.");
            }
        }

        simulation.closeWindow();
    }

    /**
     * Makes all creatures on the map perform their move.
     */
    private void allCreaturesMakeMove() {
        if (map.countEntities().get(EntityType.ALL_CHICKEN) == 0 || map.countEntities().get(EntityType.ALL_FOXES) == 0) {
            LOGGER.log(Level.INFO, "Simulation ending condition met.");
            stop();
            return;
        }

        for (Creature creature : map.getAllCreatures()) {
            creature.makeMove(map);
        }

        simulation.incrementMoveCounter();
    }
}
