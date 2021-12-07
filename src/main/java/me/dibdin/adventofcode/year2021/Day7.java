package me.dibdin.adventofcode.year2021;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 7: The Treachery of Whales.
 * https://adventofcode.com/2021/day/7
 */
public class Day7 extends AbstractChallenge {

    int[] puzzle = null;

    /**
     * Constructor.
     */
    public Day7() {
        super("The Treachery of Whales", 2021, 7);
    }

    /**
     * Solve the puzzle
     * 
     * @param fuelUsage a function which returns the fuel usage for a given distance
     *                  travelled
     * @return the lowest fuel usage
     */
    private long solve(IntFunction<Integer> fuelUsage) {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        // find the min and max positions - anything beyond these would just add cost
        int minPosition = Arrays.stream(puzzle).min().getAsInt();
        int maxPosition = Arrays.stream(puzzle).max().getAsInt();

        int leastFuelUsed = Integer.MAX_VALUE; // assuming the least fuel used will be less than this!!

        // calculate the fuel useage for all the crabs for every possible position.
        for (int position = minPosition; position <= maxPosition; position++) {
            int fuelUsedForThisPosition = 0;
            for (int crab : puzzle) {
                int crabDistance = ((position > crab) ? position - crab : crab - position);
                fuelUsedForThisPosition += fuelUsage.apply(crabDistance);
            }
            // does this position have a lower fuel useage?
            if (fuelUsedForThisPosition < leastFuelUsed) {
                leastFuelUsed = fuelUsedForThisPosition;
            }
        }

        return leastFuelUsed;
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        return solve(n -> n);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        // fuel cost function is the sum of triangular numbers
        return solve(n -> (n * (n + 1)) / 2);

    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        puzzle = Arrays.stream(input.iterator().next().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}