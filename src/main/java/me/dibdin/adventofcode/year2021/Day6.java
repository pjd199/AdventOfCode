package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
import java.lang.IllegalStateException;

/**
 * Advent of Code Challenge 2021 - Day 6: Lanternfish .
 * https://adventofcode.com/2021/day/6
 */
public class Day6 extends AbstractChallenge {

    int[] puzzle = null;

    /**
     * Constructor.
     */
    public Day6() {
        super("Lanternfish", 2021, 6);
    }

    /**
     * Move through the lifecycle of the lantern fish for specified number of days
     * @param days the number of days to simulate
     * @return the number of fish at the end of the simulation
     */
    private long lifecycle(int days) {
        long shoal[] = new long[9];

        // add the fish to the shoal, with one bucket for each day
        for (int timer : puzzle) {
            shoal[timer]++;
        }

        // for each day
        for (int day = 0; day < days; day++) {
            // decreament the timer, moving fish into the next bucket
            long nextDay[] = new long[shoal.length];
            for (int timer = 8; timer > 0; timer--) {
                nextDay[timer -1] = shoal[timer];
            }
            nextDay[6] += shoal[0]; // reset timer
            nextDay[8] += shoal[0]; // add the spawn
            shoal = nextDay;
        }

        // return the total number of fish in the shoal
        return Arrays.stream(shoal).sum();

    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        return lifecycle(80);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        return lifecycle(256);
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        Iterator<String> iterator = input.iterator();
        String[] tokens = iterator.next().split(",");

        puzzle = Stream.of(tokens).mapToInt(Integer::parseInt).toArray();
    }
}