package me.dibdin.adventofcode.util;

/**
 * A three dimensional vector, with a start position and and end position
 */
public class Vector3D {
    /**
     * The start point of this vector
     */
    public Point3D start;
    /**
     * The end point of this vector
     */
    public Point3D end;

    /**
     * Initialize a new vector, with the specified start and end points
     * @param start the start point of this vector
     * @param end the end point of this vector
     */
    public Vector3D(Point3D start, Point3D end) {
        this.start = new Point3D(start);
        this.end = new Point3D(end);
    }

    /**
     * Calculate the distance squared between the start and end points
     * @return the distance squared
     */
    public int getDistanceSquared() {
        int xDist = start.x - end.x;
        int yDist = start.y - end.y;
        int zDist = start.z - end.z;
        return (xDist * xDist) + (yDist * yDist) + (zDist * zDist);
    }

    /**
     * Calculate the distance between the start and the end points
     * @return the distance
     */
    public double getDistance() {
        return Math.sqrt(getDistanceSquared());
    }

    /**
     * Calculate the Manattan distance between the start and end points
     * @return
     */
    public int getManattanDistance() {
        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y) + Math.abs(start.z - end.z);
    }

    /**
     * Compares this object with the specified object, and returns true if the parameter
     * is a Vector3D object with matching start and end points
     * @param obj the object to compare
     * @return True if equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector3D) {
            Vector3D v = (Vector3D) obj;
            return (start.equals(v.start) && end.equals(v.end));
        } else {
            return false;
        }
    }

    /**
     * Calculates a hash code for this vector, based on the hashcodes of the
     * start and end points
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return start.hashCode() + (37 * end.hashCode());
    }

    /**
     * Creates a String representation of this vector
     */
    @Override
    public String toString() {
        return start + " -> " + end + " [ds " + getDistanceSquared() + "]";
    }
}