package com.toropov.oleg.map;

import java.util.Objects;

/**
 * The Coordinates class represents a point in a 2D space with x and y coordinates.
 */
public class Coordinates {
    private final int x;
    private final int y;

    /**
     * Constructs a new Coordinates object with specified x and y values.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Shifts the current coordinates by the specified shift values.
     *
     * @param shift the coordinates by which to shift the current coordinates.
     * @return a new Coordinates object representing the shifted coordinates.
     */
    public Coordinates shift(Coordinates shift) {
        return new Coordinates(this.x + shift.getX(), this.y + shift.getY());
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if ((x != that.x)) return false;
        return y == that.y;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }
}
