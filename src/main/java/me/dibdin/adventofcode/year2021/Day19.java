package me.dibdin.adventofcode.year2021;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;
import me.dibdin.adventofcode.util.Point3D;
import me.dibdin.adventofcode.util.Vector3D;

/**
 * Advent of Code Challenge 2021 - Day 19: Beacon Scanner.
 * https://adventofcode.com/2021/day/19
 */
public class Day19 extends AbstractChallenge {

    // puzzle data: scanner, point
    ArrayList<Point3D[]> puzzle = null;
    boolean readyToSolve = false;

    /**
     * Constructor.
     */
    public Day19() {
        super("Beacon Scanner", 2021, 19);
    }

    /**
     * Utiltiy class to record matches between scanners
     */
    class ScannerMatches {
        int scanner;
        int count;

        int getCount() {
            return count;
        }
    }

    /**
     * Utility class to help find rotation and offset matches
     */
    class RotationAndOffsetMatches {
        int rotation;
        int x;
        int y;
        int z;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof RotationAndOffsetMatches) {
                RotationAndOffsetMatches o = (RotationAndOffsetMatches) obj;
                return ((rotation == o.rotation) && (x == o.x) && (y == o.y) && (z == o.z));
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return (rotation * 37) + (x * 499) + (y * 997) + (x * 1987);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "," + z + "), r=" + rotation;
        }
    }

    /**
     * Enum type to request specific return types
     */
    private enum ReturnStat {
        NUMBER_OF_BEACONS,
        MAX_MANHATTAN_DISTANCE_BETWEEN_SCANNERS
    }

    /**
     * Rotate a Point around (0,0,0) in 90 degree steps, with the specific rotation
     * being numbered between 0 - 23.
     * 
     * @param point    the point to rotation
     * @param rotation the rotation index
     * @throws InvalidParameterException throws if rotation is not betwwen 0 - 23
     * @return a new Point3D with the applied rotation
     */
    private Point3D rotate(Point3D point, int rotation) throws InvalidParameterException {
        switch (rotation) {
            // positive x
            case 0:
                return new Point3D(point.x, point.y, point.z);
            case 1:
                return new Point3D(point.x, -point.z, point.y);
            case 2:
                return new Point3D(point.x, -point.y, -point.z);
            case 3:
                return new Point3D(point.x, point.z, -point.y);
            // negative x
            case 4:
                return new Point3D(-point.x, -point.y, point.z);
            case 5:
                return new Point3D(-point.x, point.z, point.y);
            case 6:
                return new Point3D(-point.x, point.y, -point.z);
            case 7:
                return new Point3D(-point.x, -point.z, -point.y);
            // positive y
            case 8:
                return new Point3D(point.y, point.z, point.x);
            case 9:
                return new Point3D(point.y, -point.x, point.z);
            case 10:
                return new Point3D(point.y, -point.z, -point.x);
            case 11:
                return new Point3D(point.y, point.x, -point.z);
            // negative y
            case 12:
                return new Point3D(-point.y, -point.z, point.x);
            case 13:
                return new Point3D(-point.y, point.x, point.z);
            case 14:
                return new Point3D(-point.y, point.z, -point.x);
            case 15:
                return new Point3D(-point.y, -point.x, -point.z);
            // positive z
            case 16:
                return new Point3D(point.z, point.x, point.y);
            case 17:
                return new Point3D(point.z, -point.y, point.x);
            case 18:
                return new Point3D(point.z, -point.x, -point.y);
            case 19:
                return new Point3D(point.z, point.y, -point.x);
            // negative z
            case 20:
                return new Point3D(-point.z, -point.x, point.y);
            case 21:
                return new Point3D(-point.z, point.y, point.x);
            case 22:
                return new Point3D(-point.z, point.x, -point.y);
            case 23:
                return new Point3D(-point.z, -point.y, -point.x);
        }

        throw new InvalidParameterException();
    }

    /**
     * Create a new vector, with start and end points rotated by the requested
     * index, between 0 - 23
     * 
     * @param vector   the vector to rotate
     * @param rotation the rotation index, 0 - 23
     * @throws InvalidParameterException throws if rotation index is not between 0 -
     *                                   23
     * @return a newly created and rotated vector
     */
    private Vector3D rotate(Vector3D vector, int rotation) throws InvalidParameterException {
        return new Vector3D(rotate(vector.start, rotation), rotate(vector.end, rotation));
    }

    /**
     * Solve the puzzle, and return the requested statistic.
     * 1) create vectors all all the points detected by each sensor
     * 2) comparing the squared distance of vectors, work out the number of matches
     * in lengths between pairs of scanners.
     * 3) starting with scanner zero, start adding the best matching scanner that
     * hasn't yet
     * been added to the map. Work out the best rotation and offset that fits with
     * the start
     * and end of the vector. Add this vector and it's rotated beacons to the map.
     * 4) Repeat step 3 until all scanners have been added.
     * 
     * @param returnStat the requested statistic
     * @return the value of the statistic
     */
    private long solve(ReturnStat returnStat) {

        // create vector objects for all possible pairs in each of the vectors
        ArrayList<Vector3D[]> scannerVectors = new ArrayList<Vector3D[]>();
        ArrayList<Vector3D> vectors = new ArrayList<Vector3D>();

        for (Point3D[] scanner : puzzle) {
            for (int i = 0; i < scanner.length; i++) {
                for (int j = 0; j < scanner.length; j++) {
                    if (i != j) {
                        vectors.add(new Vector3D(scanner[i], scanner[j]));
                    }
                }
            }
            vectors.sort(Comparator.comparingInt(Vector3D::getDistanceSquared));
            scannerVectors.add(vectors.toArray(new Vector3D[vectors.size()]));
            vectors.clear();
        }

        // for each scanner, find another scanner which overlaps
        ArrayList<ArrayList<ScannerMatches>> scannerMatches = new ArrayList<ArrayList<ScannerMatches>>();

        for (int scanner = 0; scanner < puzzle.size(); scanner++) {
            ArrayList<ScannerMatches> matches = new ArrayList<ScannerMatches>();
            for (int pair = 0; pair < puzzle.size(); pair++) {
                if (scanner != pair) {

                    ScannerMatches counter = new ScannerMatches();
                    counter.scanner = pair;
                    counter.count = 0;

                    Vector3D[] scannerVectorArray = scannerVectors.get(scanner);
                    Vector3D[] pairVectorArray = scannerVectors.get(pair);

                    // work through the list of vectors to find matches
                    int s = 0;
                    int p = 0;
                    while ((s < scannerVectorArray.length) && (p < pairVectorArray.length)) {
                        if (scannerVectorArray[s].getDistanceSquared() == pairVectorArray[p].getDistanceSquared()) {
                            // found a matching vector
                            counter.count++;
                            s++;
                            p++;
                        } else if (scannerVectorArray[s].getDistanceSquared() < pairVectorArray[p]
                                .getDistanceSquared()) {
                            s++;
                        } else {
                            p++;
                        }
                    }
                    matches.add(counter);
                }
            }
            matches.sort(Comparator.comparingInt(ScannerMatches::getCount).reversed());
            scannerMatches.add(matches);
        }

        // Stores the list of all scanners with their beacons, aligned to scanner zero
        Point3D[][] alignedScanners = new Point3D[puzzle.size()][];

        // Stores the list of the vectors, once the scanner has been aligned
        Vector3D[][] alignedVectors = new Vector3D[puzzle.size()][];

        // Stores the scanner positions relative to scanner 0
        Point3D[] scannerPositions = new Point3D[puzzle.size()];

        // Add scanner zero to the map
        alignedScanners[0] = Arrays.copyOf(puzzle.get(0), puzzle.get(0).length);
        alignedVectors[0] = Arrays.copyOf(scannerVectors.get(0), scannerVectors.get(0).length);
        scannerPositions[0] = new Point3D(0, 0, 0);

        // starting with scanner zero, expand the map, rotating scanners to
        // align with scanner zero
        boolean matchFound;
        do {
            matchFound = false;

            // find the best match to a scanner we already have in the map
            int scannerInMap = -1;
            int scannerPair = -1;
            int bestCount = Integer.MIN_VALUE;
            for (int scanner = 1; scanner < puzzle.size(); scanner++) {
                if (scannerPositions[scanner] == null) {
                    for (ScannerMatches matches : scannerMatches.get(scanner)) {
                        if ((scannerPositions[matches.scanner] != null) && (matches.count > bestCount)) {
                            bestCount = matches.count;
                            scannerInMap = matches.scanner;
                            scannerPair = scanner;
                            matchFound = true;
                        }
                    }
                }
            }

            // can we add another scanner to the map?
            if (matchFound) {
                // find the rotation that gives the most consistent x,y,z offset
                HashMap<RotationAndOffsetMatches, Integer> matchCounts = new HashMap<RotationAndOffsetMatches, Integer>();

                Vector3D[] scannerVectorArray = alignedVectors[scannerInMap];
                Vector3D[] pairVectorArray = scannerVectors.get(scannerPair);

                // work through the list of vectors to find matches
                int s = 0;
                int p = 0;
                while ((s < scannerVectorArray.length) && (p < pairVectorArray.length)) {
                    if (scannerVectorArray[s].getDistanceSquared() == pairVectorArray[p].getDistanceSquared()) {
                        // found a matching vector, so try rotating the pair, so that the x,y,z offset
                        // between the two start points is the same for the end point
                        for (int r = 0; r < 24; r++) {
                            // rotate the vector
                            Vector3D rotatedVector = rotate(pairVectorArray[p], r);

                            // calculate thes offset between the start and end points
                            int xDiffStart = scannerVectorArray[s].start.x - rotatedVector.start.x;
                            int xDiffEnd = scannerVectorArray[s].end.x - rotatedVector.end.x;
                            int yDiffStart = scannerVectorArray[s].start.y - rotatedVector.start.y;
                            int yDiffEnd = scannerVectorArray[s].end.y - rotatedVector.end.y;
                            int zDiffStart = scannerVectorArray[s].start.z - rotatedVector.start.z;
                            int zDiffEnd = scannerVectorArray[s].end.z - rotatedVector.end.z;

                            // do the offsets at the start match the offsets at the end?
                            if ((xDiffStart == xDiffEnd) && (yDiffStart == yDiffEnd) && (zDiffStart == zDiffEnd)) {
                                // success
                                RotationAndOffsetMatches match = new RotationAndOffsetMatches();
                                match.rotation = r;
                                match.x = xDiffStart;
                                match.y = yDiffStart;
                                match.z = zDiffStart;

                                // increament the match count
                                if (matchCounts.containsKey(match)) {
                                    matchCounts.put(match, matchCounts.get(match) + 1);
                                } else {
                                    matchCounts.put(match, 1);
                                }
                            }
                        }
                        s++;
                        p++;
                    } else if (scannerVectorArray[s].getDistanceSquared() < pairVectorArray[p].getDistanceSquared()) {
                        s++;
                    } else {
                        p++;
                    }
                }

                // find the best match
                RotationAndOffsetMatches bestMatch = null;
                int bestMatchCount = Integer.MIN_VALUE;

                for (RotationAndOffsetMatches key : matchCounts.keySet()) {
                    int count = matchCounts.get(key);
                    if (count > bestMatchCount) {
                        bestMatch = key;
                        bestMatchCount = matchCounts.get(key);
                    }
                }

                // save this scanner's position, relative to scanner zero
                scannerPositions[scannerPair] = new Point3D(bestMatch.x, bestMatch.y, bestMatch.z);

                // rotate and align this scanner's beacons relative to scanner zero
                alignedScanners[scannerPair] = new Point3D[puzzle.get(scannerPair).length];
                for (int i = 0; i < alignedScanners[scannerPair].length; i++) {
                    Point3D rotated = rotate(puzzle.get(scannerPair)[i], bestMatch.rotation);
                    alignedScanners[scannerPair][i] = new Point3D(rotated.x + bestMatch.x, rotated.y + bestMatch.y,
                            rotated.z + bestMatch.z);
                }

                // finally, calculate rotated all the vectors for this scanner
                ArrayList<Vector3D> v = new ArrayList<Vector3D>();
                for (int i = 0; i < alignedScanners[scannerPair].length; i++) {
                    for (int j = 0; j < alignedScanners[scannerPair].length; j++) {
                        if (i != j) {
                            v.add(new Vector3D(alignedScanners[scannerPair][i], alignedScanners[scannerPair][j]));
                        }
                    }
                }
                v.sort(Comparator.comparingInt(Vector3D::getDistanceSquared));
                alignedVectors[scannerPair] = v.toArray(new Vector3D[v.size()]);
            }

        } while (matchFound);

        // Calculate the final statistic
        switch (returnStat) {
            // return the number of unique beacons in the puzzle
            case NUMBER_OF_BEACONS:
                HashSet<Point3D> beaconPositions = new HashSet<Point3D>();
                for (Point3D[] scannerBeacons : alignedScanners) {
                    beaconPositions.addAll(Arrays.asList(scannerBeacons));
                }
                return beaconPositions.size();

            // work out the greatest Manhattan distance between the scanners
            case MAX_MANHATTAN_DISTANCE_BETWEEN_SCANNERS:
                long maxManhattanDistance = Long.MIN_VALUE;
                for (int i = 0; i < scannerPositions.length; i++) {
                    for (int j = 0; j < scannerPositions.length; j++) {
                        if (i != j) {
                            long distance = new Vector3D(scannerPositions[i], scannerPositions[j])
                                    .getManattanDistance();
                            if (distance > maxManhattanDistance) {
                                maxManhattanDistance = distance;
                            }
                        }
                    }
                }
                return maxManhattanDistance;

            // impossible to get here get here!!!
            default:
                return -1;
        }
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        return solve(ReturnStat.NUMBER_OF_BEACONS);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        return solve(ReturnStat.MAX_MANHATTAN_DISTANCE_BETWEEN_SCANNERS);
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        // load all the scanners from the input
        puzzle = new ArrayList<Point3D[]>();

        // regular expression for x,y,z integers
        Pattern regex = Pattern.compile("(-?[0-9]+),(-?[0-9]+),(-?[0-9]+)");

        Iterator<String> iterator = input.iterator();
        ArrayList<Point3D> currentScanner = null;
        while (iterator.hasNext()) {
            String line = iterator.next();
            Matcher matcher = regex.matcher(line);

            if (matcher.matches()) {
                // found a line with beacon's x,y,z position
                currentScanner.add(new Point3D(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))));
            } else if (line.contains("scanner")) {
                // found the start of a new scanner
                if (currentScanner != null) {
                    // store the scanner that we've just finished reading
                    puzzle.add(currentScanner.toArray(new Point3D[currentScanner.size()]));
                }
                currentScanner = new ArrayList<Point3D>();
            }
        }

        // add the final scanner, if required
        if ((currentScanner != null) && (currentScanner.size() > 0)) {
            puzzle.add(currentScanner.toArray(new Point3D[currentScanner.size()]));
        }

        readyToSolve = true;
    }
}