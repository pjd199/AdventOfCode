package me.dibdin.adventofcode.year2021;

import java.util.Arrays;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;
import me.dibdin.adventofcode.util.InputDecoder;

/**
 * Advent of Code Challenge 2021 - Day 11: Dumbo Octopus.
 * https://adventofcode.com/2021/day/11
 */
public class Day11 extends AbstractChallenge {

    int[][] puzzle = null;

    /**
     * Constructor.
     */
    public Day11() {
        super("Dumbo Octopus", 2021, 11);
    }

    /**
     * Increament the given element of the array if it is a valid index
     * 
     * @param array the array to increament
     * @param i     the first dimensional index of the array
     * @param j     the second dimensional index of the array
     * @return True if index was valid, otherwise false
     */
    private void increamentIfValid(int[][] array, int i, int j) {
        if ((i >= 0) && (i < array.length) && (j >= 0) && (j < array[i].length)) {
            array[i][j]++;
        }
    }

    /**
     * Helper class to return two results from the simulation
     */
    private class SimulationResults {
        private long totalFlashes = 0;
        private long finalCycle = 0;
    }

    /**
     * Run the simulation, for the specified number of cycles, optionally ending
     * when all flash
     * 
     * @param cycles          the number of cycles to run
     * @param endWhenAllFlash when true, ends when all octopuses flash
     * @return the total number of flash and the final cycle
     */
    private SimulationResults runSimulation(int cycles, boolean endWhenAllFlash) {

        // help object to return multiple results
        SimulationResults results = new SimulationResults();

        // copy the puzzle array
        int[][] octopusArray = Arrays.stream(puzzle).map(int[]::clone).toArray(int[][]::new);

        // run the simulation for 100 increaments
        for (int time = 1; time <= cycles; time++) {

            // increament the cycle
            results.finalCycle++;

            // first, increase energy levels by 1
            for (int i = 0; i < octopusArray.length; i++) {
                for (int j = 0; j < octopusArray[i].length; j++) {
                    octopusArray[i][j]++;
                }
            }

            // find all the octopus that flash
            boolean flashedThisLoop = true;
            boolean[][] flashes = new boolean[octopusArray.length][octopusArray[0].length];

            while (flashedThisLoop) {

                // only repeat loop if an octopus flashes
                flashedThisLoop = false;

                // for each of the octopuses - do they flash?
                for (int i = 0; i < octopusArray.length; i++) {
                    for (int j = 0; j < octopusArray[i].length; j++) {
                        if ((octopusArray[i][j] > 9) && !flashes[i][j]) {
                            flashedThisLoop = true;
                            flashes[i][j] = true;

                            // increase energy around
                            increamentIfValid(octopusArray, i - 1, j - 1);
                            increamentIfValid(octopusArray, i - 1, j);
                            increamentIfValid(octopusArray, i - 1, j + 1);
                            increamentIfValid(octopusArray, i, j - 1);
                            increamentIfValid(octopusArray, i, j + 1);
                            increamentIfValid(octopusArray, i + 1, j - 1);
                            increamentIfValid(octopusArray, i + 1, j);
                            increamentIfValid(octopusArray, i + 1, j + 1);
                        }
                    }
                }
            }

            // Count the number of flashes and reset the
            // energy of the octopuses that have flashed
            int numberOfFlashes = 0;
            for (int i = 0; i < octopusArray.length; i++) {
                for (int j = 0; j < octopusArray[i].length; j++) {
                    if (flashes[i][j]) {
                        octopusArray[i][j] = 0;
                        numberOfFlashes++;
                    }
                }
            }
            results.totalFlashes += numberOfFlashes;

            // optionally end when all have flashed
            if (endWhenAllFlash && (numberOfFlashes == (flashes.length * flashes[0].length))) {
                return results;
            }
        }

        return results;
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        // run the simulation 100 times
        SimulationResults results = runSimulation(100, false);
        return results.totalFlashes;
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        // run the simulation until all the octopus flash
        SimulationResults results = runSimulation(Integer.MAX_VALUE, true);
        return results.finalCycle;
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        puzzle = InputDecoder.decodeAsTwoDimensionalIntArray(input);
    }
}