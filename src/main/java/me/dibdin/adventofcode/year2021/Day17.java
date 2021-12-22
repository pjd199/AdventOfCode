package me.dibdin.adventofcode.year2021;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 17: Trick Shot.
 * https://adventofcode.com/2021/day/X
 */
public class Day17 extends AbstractChallenge {

    // puzzle data
    int targetMinX;
    int targetMinY;
    int targetMaxX;
    int targetMaxY;
    boolean readyToSolve = false;

    enum ReturnStat {
        HEIGHT, HITS
    };

    /**
     * Constructor.
     */
    public Day17() {
        super("Trick Shot", 2021, 17);
    }

    /**
     * Run the simulation by taking multiple shots at the target
     * @param returnStat which statistic should be returned - max trajectory height or number of hits on the target
     * @return the value of the requested statistic
     */
    private int runSimulation(ReturnStat returnStat) {
        // initialise the stats
        int highestYPosition = Integer.MIN_VALUE;
        int hits = 0;

        // find the minimum velocity X, using the formula for triangular numbers
        int triangleNumberSeries = 1;
        while (((triangleNumberSeries * (triangleNumberSeries + 1)) / 2) < targetMinX) {
            triangleNumberSeries++;
        }
        triangleNumberSeries--;
        int minimumVelocityX = triangleNumberSeries;

        // find the maximum velocity X
        int maximumVelocityX = targetMaxX;

        // find the maximum velocity Y - but just a guess
        int maximumVelcityY = 100;

        // find the minimum velocity Y
        int minimumVelocityY = targetMinY;

        // find all the possible "on target" shots
        for (int velocityY = minimumVelocityY; velocityY <= maximumVelcityY; velocityY++) {
            for (int velocityX = minimumVelocityX; velocityX <= maximumVelocityX; velocityX++) {
                
                // get ready to take this shot
                int probeVelocityX = velocityX;
                int probeVelocityY = velocityY;
                int x = 0;
                int y = 0;
                int height = Integer.MIN_VALUE;

                // take the shot, looping until we overshot the target
                while ((x <= targetMaxX) && (y >= targetMinY)) {

                    // have we reached a new highest position for this shot?
                    if (y > height) {
                        height = y;
                    }

                    // have we hit the target
                    if ((targetMinX <= x) && (x <= targetMaxX) && (y <= targetMaxY) && (y >= targetMinY)) {
                        // bullseye!!!
                        hits++;

                        // did this shot set a new height record?
                        if (height > highestYPosition) {
                            highestYPosition = height;
                        }
                        break;
                    }

                    // advance to next step
                    x += probeVelocityX;
                    y += probeVelocityY;

                    // adjust the velocity due to water resistence and gravity
                    if (probeVelocityX > 0) {
                        probeVelocityX--;
                    } else if (probeVelocityX < 0) {
                        probeVelocityX++;
                    }
                    probeVelocityY--;
                }
            }
        }

        // all simulations complete, so return the requested statistic
        switch (returnStat) {
            case HEIGHT:
                return highestYPosition;
            case HITS:
                return hits;
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

        return runSimulation(ReturnStat.HEIGHT);

    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        return runSimulation(ReturnStat.HITS);
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        String inputString = input.iterator().next();

        // matching example: "target area: x=20..30, y=-10..-5"
        Pattern pattern = Pattern.compile("target area: x=(-?[0-9]+)..(-?[0-9]+), y=(-?[0-9]+)..(-?[0-9]+)");
        Matcher matcher = pattern.matcher(inputString);

        // store the decoded matches
        if (matcher.matches()) {
            targetMinX = Integer.parseInt(matcher.group(1));
            targetMaxX = Integer.parseInt(matcher.group(2));
            targetMinY = Integer.parseInt(matcher.group(3));
            targetMaxY = Integer.parseInt(matcher.group(4));
        }

        readyToSolve = true;
    }
}