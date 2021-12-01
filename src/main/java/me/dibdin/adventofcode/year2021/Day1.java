package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.stream.Stream;
import java.lang.IllegalStateException;

public class Day1 extends AbstractChallenge {

    int[] puzzle = null;

    public Day1() {
        super ("Sonar Sweep", 2021, 1);
    }

    public long solvePartOne() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        long count = 0;

        for (int i = 1; i < puzzle.length; i++) {
            if (puzzle[i] > puzzle[i-1]) {
                count++;
            }
        }

        return count;
    }

    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        long count = 0;

        int average = puzzle[0] + puzzle[1] + puzzle[0];

        for (int i = 1; i < puzzle.length - 2; i++) {
            int newAverage = puzzle[i] + puzzle[i+1] + puzzle[i+2]; 
            if (newAverage > average) {
                count++;
            }
            average = newAverage;
        }

        return count;
    }

    public void setPuzzleInput(Stream<String> input) {
        this.puzzle = input.mapToInt(Integer::parseInt).toArray(); 
    }
}