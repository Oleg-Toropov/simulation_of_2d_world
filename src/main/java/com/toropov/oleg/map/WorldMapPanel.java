package com.toropov.oleg.map;

import com.toropov.oleg.entity.Creature;
import com.toropov.oleg.entity.Entity;
import com.toropov.oleg.entity.herbivore.Chickens;
import com.toropov.oleg.entity.predator.Foxes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * WorldMapPanel is a custom JPanel for displaying a 2D simulation world map with entities.
 */
public class WorldMapPanel extends JPanel {

    /**
     * The world map displayed on this panel.
     */
    private WorldMap map;

    /**
     * The size of the panel.
     */
    private static final int PANEL_SIZE = 800;

    /**
     * The size of each cell in the grid.
     */
    private static final int CELL_SIZE = 40;

    /**
     * The font size for displaying text on the panel.
     */
    private static final int FONT_SIZE = 12;

    /**
     * The background color of the panel.
     */
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    /**
     * The default text color for displaying text on the panel.
     */
    private static final Color TEXT_COLOR = Color.BLACK;

    /**
     * The text color for displaying information about foxes.
     */
    private static final Color FOX_TEXT_COLOR = Color.BLUE;

    /**
     * Collection of images used to render entities on the map.
     */
    private final Map<String, Image> images;

    /**
     * Constructs a WorldMapPanel with a given map.
     *
     * @param map the WorldMap to be displayed
     */
    public WorldMapPanel(WorldMap map) {
        this.map = map;
        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        setBackground(BACKGROUND_COLOR);

        images = new HashMap<>();
        loadImages();
    }

    /**
     * Loads images for different entity types.
     */
    private void loadImages() {
        images.put("MaleFox", loadImage("/maleFox.png"));
        images.put("FemaleFox", loadImage("/femaleFox.png"));
        images.put("FoxCub", loadImage("/foxCub.png"));
        images.put("Rooster", loadImage("/rooster.png"));
        images.put("Hen", loadImage("/hen.png"));
        images.put("Chick", loadImage("/chick.png"));
        images.put("Grass", loadImage("/grass.png"));
        images.put("Rock", loadImage("/rock.png"));
        images.put("Tree", loadImage("/tree.png"));
    }

    /**
     * Loads an image from the given path.
     *
     * @param path the path to the image file
     * @return the loaded Image
     */
    private Image loadImage(String path) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage();
    }

    /**
     * Sets the WorldMap to be displayed and repaints the panel.
     *
     * @param map the WorldMap to be displayed
     */
    public void setMap(WorldMap map) {
        this.map = map;
        repaint();
    }

    /**
     * Paints the component, rendering the world map and its entities.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);
    }

    /**
     * Draws the entire map with entities.
     *
     * @param g the Graphics context
     */
    private void drawMap(Graphics g) {
        for (int y = 19; y >= 0; y--) {
            for (int x = 0; x < 20; x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (map.isSquareEmpty(coordinates)) {
                    drawEmptySquare(g, coordinates);
                } else {
                    drawEntity(g, map.getEntity(coordinates));
                }
            }
        }
    }

    /**
     * Draws an empty square at the specified coordinates.
     *
     * @param g the Graphics context
     * @param coordinates the coordinates of the square to be drawn
     */
    private void drawEmptySquare(Graphics g, Coordinates coordinates) {
        int x = coordinates.getX() * CELL_SIZE;
        int y = (19 - coordinates.getY()) * CELL_SIZE;

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    }

    /**
     * Draws an entity at its coordinates.
     *
     * @param g the Graphics context
     * @param entity the Entity to be drawn
     */
    private void drawEntity(Graphics g, Entity entity) {
        int x = entity.getCoordinates().getX() * CELL_SIZE;
        int y = (19 - entity.getCoordinates().getY()) * CELL_SIZE;

        String entityType = entity.getClass().getSimpleName();
        Image image = images.get(entityType);

        if (image != null) {
            g.drawImage(image, x, y, CELL_SIZE, CELL_SIZE, this);
        }

        if (entity instanceof Creature creature) {
            drawCreatureInfo(g, x, y, creature);
        }
    }

    /**
     * Draws additional information about a creature such as health points and generation.
     *
     * @param g the Graphics context
     * @param x the x-coordinate of the creature
     * @param y the y-coordinate of the creature
     * @param creature the Creature whose information is to be drawn
     */
    private void drawCreatureInfo(Graphics g, int x, int y, Creature creature) {
        if (creature instanceof Foxes) {
            g.setColor(FOX_TEXT_COLOR);
        } else if (creature instanceof Chickens) {
            g.setColor(TEXT_COLOR);
        }

        Font originalFont = g.getFont();
        Font boldFont = originalFont.deriveFont(Font.BOLD, FONT_SIZE);
        g.setFont(boldFont);

        g.drawString("HP " + creature.getHealthPoints(), x + CELL_SIZE - 40, y + 38);
        g.drawString("Gen " + creature.getGeneration(), x + CELL_SIZE - 40, y + 10);

        g.setFont(originalFont);
    }
}
