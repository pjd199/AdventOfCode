package me.dibdin.adventofcode.util;

/**
 * Represents a point in three dimensional space, with an (x,y,z) co-ordinate 
 */
public class Point3D {
    /**
     * The X position value of this point
     */
    public int x;
    /**
     * The Y position value of this point
     */
    public int y;
    /**
     * The Z position value of this point
     */
    public int z;

    /**
     * Create a new point with the specified posistion
     * @param x the X position of this point
     * @param y the Y position of this point
     * @param z the Z position of this point
     */
    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new point with the same position as the parameter
     * @param p the Point3D to copy
     */
    public Point3D(Point3D p) {
        this(p.x, p.y, p.z);
    }

    /**
     * Returns true if the given object has the same (x,y,z) position as this Point
     * @param obj The object to compare for equality
     * @return True if equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point3D) {
            Point3D other = (Point3D) obj;
            return ((x == other.x) && (y == other.y) && (z == other.z));
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
        return (100003 * x) + (1009 * y) + z;
    }

    /**
     * Returns a string representation of this Point3D, in the format of "(x,y,z)"
     * @return the formatted string
     */
    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", x, y, z);
    }
}