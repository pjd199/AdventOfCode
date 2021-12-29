package me.dibdin.adventofcode.util;

/**
 * Represents a point in two dimensional space, with an (x,y) co-ordinate 
 */
public class Point2D {
    /**
     * The X position value of this point
     */
    public int x;
    /**
     * The Y position value of this point
     */
    public int y;

    /**
     * Create a new point with the specified posistion
     * @param x the X position of this point
     * @param y the Y position of this point
     */
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new point with the same position as the parameter
     * @param p the Point2D to copy
     */
    public Point2D(Point2D p) {
        this(p.x, p.y);
    }

    /**
     * Returns true if the given object has the same (x,y) position as this Point
     * @param obj The object to compare for equality
     * @return True if equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point2D) {
            Point2D other = (Point2D) obj;
            return ((x == other.x) && (y == other.y));
        } else {
            return false;
        }
    }

    
    /**
     * Return the hashcode for this point
     * @return the hashcode for this point
     */
    @Override
    public int hashCode() {
        return (1009 * x) + (37 * y);
    }

    /**
     * Returns a string representation of this Point3D, in the format of "(x,y)"
     * @return the formatted string
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}