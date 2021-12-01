package me.dibdin.adventofcode.year2020;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.stream.Stream;

public class Day1 extends AbstractChallenge {

    public Day1() {
        super ("Report Repair", 2020, 1);
    }

    public int solvePartOne(int[] intArray) {
        int i, j, sum, product;

        // Go through the numbers, looking for the pair that adds up to 2020, with the result being the product of the numbers
        for (i = 0; i < intArray.length; i++) {
            for (j = (i + 1); j < intArray.length; j++) {
                sum = intArray[i] + intArray[j];
                if (sum == 2020) {
                    product = intArray[i] * intArray[j];
                    //System.out.printf("%d + %d = %d, so the product of %d * %d = %d\n", intArray[i], intArray[j], sum, intArray[i], intArray[j], product);
                    return product;
                }
            }
        }

        return -1;
    }

    public int solvePartTwo(int[] intArray) {
        int i, j, k, sum, product;

        // Go through the numbers, looking for the trouple that adds up to 2020, with the result being the product of the numbers
        for (i = 0; i < intArray.length; i++) {
            for (j = (i + 1); j < intArray.length; j++) {
                for (k = (j + 1); k < intArray.length; k++) {
                    sum = intArray[i] + intArray[j] + intArray[k];
                    if (sum == 2020) {
                        product = intArray[i] * intArray[j] * intArray[k];
                        //System.out.printf("%d + %d + %d = %d, so the product of %d * %d * %d = %d\n", intArray[i], intArray[j], intArray[k], sum, intArray[i], intArray[j], intArray[k], product);
                        return product;
                    }
                }
            }
        }

        return -1;
    }

    public void solve(Stream<String> input) {
        
        // Convert the input into an array of integers
        int[] intArray = input.mapToInt(Integer::parseInt).toArray(); 

        // Solve the puzzles
        setPartOneResult(Integer.toString(solvePartOne(intArray)));
        setPartTwoResult(Integer.toString(solvePartTwo(intArray)));
    }
}