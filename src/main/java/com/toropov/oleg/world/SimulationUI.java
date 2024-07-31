package com.toropov.oleg.world;

import com.toropov.oleg.map.EntityType;
import com.toropov.oleg.map.WorldMapPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * The SimulationUI class manages the user interface for the simulation.
 * It includes controls for starting, pausing, and stopping the simulation,
 * as well as displaying counts of different entities and the move counter.
 */
public class SimulationUI {
    private final JFrame frame;
    private final JLabel roosterCountLabel;
    private final JLabel henCountLabel;
    private final JLabel grassCountLabel;
    private final JLabel maleFoxCountLabel;
    private final JLabel femaleFoxCountLabel;
    private final JLabel moveCounterLabel;

    /**
     * Constructs the SimulationUI.
     *
     * @param panel the panel where the simulation is displayed
     * @param startAction the action to perform when the start button is pressed
     * @param pauseAction the action to perform when the pause button is pressed
     * @param stopAction the action to perform when the stop button is pressed
     */
    public SimulationUI(WorldMapPanel panel, Runnable startAction, Runnable pauseAction, Runnable stopAction) {
        frame = new JFrame("2D World Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(815, 880);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton stopButton = new JButton("Stop");

        roosterCountLabel = new JLabel("Herbivores: 0");
        henCountLabel = new JLabel("Herbivores: 0");
        grassCountLabel = new JLabel("Grass: 0");
        maleFoxCountLabel = new JLabel("MaleFox: 0");
        femaleFoxCountLabel = new JLabel("FemaleFox: 0");
        moveCounterLabel = new JLabel("Move counter: 0");

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);

        controlPanel.add(roosterCountLabel);
        controlPanel.add(henCountLabel);
        controlPanel.add(grassCountLabel);
        controlPanel.add(maleFoxCountLabel);
        controlPanel.add(femaleFoxCountLabel);
        controlPanel.add(moveCounterLabel);

        frame.add(controlPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startAction.run());
        pauseButton.addActionListener(e -> pauseAction.run());
        stopButton.addActionListener(e -> stopAction.run());

        frame.setVisible(true);
    }

    /**
     * Updates the labels displaying the counts of different entities and the move counter.
     *
     * @param countEntities a map of entity types to their counts
     * @param moveCounter the current move counter
     */
    public void updateCounts(Map<EntityType, Integer> countEntities, int moveCounter) {
        roosterCountLabel.setText("Rooster: " + countEntities.get(EntityType.ROOSTER));
        henCountLabel.setText("Hen: " + countEntities.get(EntityType.HEN));
        grassCountLabel.setText("Grass: " + countEntities.get(EntityType.GRASS));
        maleFoxCountLabel.setText("MaleFox: " + countEntities.get(EntityType.MALE_FOX));
        femaleFoxCountLabel.setText("FemaleFox: " + countEntities.get(EntityType.FEMALE_FOX));
        moveCounterLabel.setText("Move counter: " + moveCounter);
    }

    /**
     * Disposes of the frame, closing the user interface window.
     */
    public void dispose() {
        frame.dispose();
    }
}
