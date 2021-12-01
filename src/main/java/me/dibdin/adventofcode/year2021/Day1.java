package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.stream.Stream;

public class Day1 extends AbstractChallenge {

    public Day1() {
        super ("Sonar Sweep", 2021, 1);
    }

    public long solvePartOne(int[] intArray) {
        long count = 0;

        for (int i = 1; i < intArray.length; i++) {
            if (intArray[i] > intArray[i-1]) {
                count++;
            }
        }

        return count;
    }

    public long solvePartTwo(int[] intArray) {
 
        long count = 0;

        int average = intArray[0] + intArray[1] + intArray[0];

        for (int i = 1; i < intArray.length - 2; i++) {
            int newAverage = intArray[i] + intArray[i+1] + intArray[i+2]; 
            if (newAverage > average) {
                count++;
            }
            average = newAverage;
        }

        return count;
    }

    public void solve(Stream<String> input) {
        
        // Convert the input into an array of integers
        int[] intArray = input.mapToInt(Integer::parseInt).toArray(); 

        // Solve the puzzles
        setPartOneResult(Long.toString(solvePartOne(intArray)));
        setPartTwoResult(Long.toString(solvePartTwo(intArray)));
    }
}