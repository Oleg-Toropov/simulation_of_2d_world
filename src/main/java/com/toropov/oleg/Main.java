package com.toropov.oleg;

import com.toropov.oleg.world.Simulation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Main class for the 2D World Simulation application.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Default constructor.
     * This class is not intended to be instantiated.
     */
    public Main() {
        // Utility class, no instances allowed.
    }

    /**
     * Main method to start the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not read logging configuration", e);
        }
        Simulation app = new Simulation();
        app.run();
    }
}
