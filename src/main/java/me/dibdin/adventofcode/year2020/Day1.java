package me.dibdin.adventofcode.year2020;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.stream.Stream;

public class Day1 extends AbstractChallenge {

    int[] puzzle = null;

    public Day1() {
        super ("Report Repair", 2020, 1);
    }

    public long solvePartOne() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        int i, j, sum, product;

        // Go through the numbers, looking for the pair that adds up to 2020, with the result being the product of the numbers
        for (i = 0; i < puzzle.length; i++) {
            for (j = (i + 1); j < puzzle.length; j++) {
                sum = puzzle[i] + puzzle[j];
                if (sum == 2020) {
                    product = puzzle[i] * puzzle[j];
                    //System.out.printf("%d + %d = %d, so the product of %d * %d = %d\n", puzzle[i], puzzle[j], sum, puzzle[i], puzzle[j], product);
                    return product;
                }
            }
        }

        return -1;
    }

    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        int i, j, k, sum, product;

        // Go through the numbers, looking for the trouple that adds up to 2020, with the result being the product of the numbers
        for (i = 0; i < puzzle.length; i++) {
            for (j = (i + 1); j < puzzle.length; j++) {
                for (k = (j + 1); k < puzzle.length; k++) {
                    sum = puzzle[i] + puzzle[j] + puzzle[k];
                    if (sum == 2020) {
                        product = puzzle[i] * puzzle[j] * puzzle[k];
                        //System.out.printf("%d + %d + %d = %d, so the product of %d * %d * %d = %d\n", puzzle[i], puzzle[j], puzzle[k], sum, puzzle[i], puzzle[j], puzzle[k], product);
                        return product;
                    }
                }
            }
        }

        return -1;
    }

    public void setPuzzleInput(Stream<String> input) {
        // Convert the input into an array of integers
        this.puzzle = input.mapToInt(Integer::parseInt).toArray(); 
    }
}