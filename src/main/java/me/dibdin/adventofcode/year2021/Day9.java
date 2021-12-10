package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.IllegalStateException;

/**
 * Advent of Code Challenge 2021 - Day 9: Smoke Basin.
 * https://adventofcode.com/2021/day/9
 */
public class Day9 extends AbstractChallenge {

    int[][] puzzle = null;

    /**
     * Constructor.
     */
    public Day9() {
        super("Smoke Basin", 2021, 9);
    }

    /**
     * Find the lowest points in the puzzle
     * 
     * @return retun a array with lowest points marked true, otherwise false
     */
    private boolean[][] findLowestPoints() {

        boolean isLowestPoint[][] = new boolean[puzzle.length][puzzle[0].length];

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if ((((i - 1) < 0) || (puzzle[i][j] < puzzle[i - 1][j])) && // up
                        (((i + 1) == puzzle.length) || (puzzle[i][j] < puzzle[i + 1][j])) && // down
                        (((j - 1) < 0) || (puzzle[i][j] < puzzle[i][j - 1])) && // left
                        (((j + 1) == puzzle[i].length) || (puzzle[i][j] < puzzle[i][j + 1])) // right
                ) {
                    isLowestPoint[i][j] = true;
                }
            }
        }

        return isLowestPoint;
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        // find the lowest points
        boolean[][] isLowestPoint = findLowestPoints();

        // add up the risk value from all the lowest points in the puzzle
        long sum = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (isLowestPoint[i][j]) {
                    sum += (puzzle[i][j] + 1);
                }
            }
        }

        return sum;
    }

    /**
     * Recursively map a basin, but putting the basin Id in every place where the
     * basin extends to
     * 
     * @param basinMap      the basin map to map out
     * @param i             the first index of the basin map array
     * @param j             the second index of the basin map array
     * @param isLowestPoint the array of lowest points
     * @param basinId       the current basin ID
     */
    private void mapBasin(int[][] basinMap, int i, int j, boolean[][] isLowestPoint, int basinId) {
        // if a valid index in the basin map and less than the top
        if (((i >= 0) && (i < basinMap.length) && (j >= 0) && (j < basinMap[i].length)) &&
                (puzzle[i][j] < 9) && (basinMap[i][j] != basinId)) {

            basinMap[i][j] = basinId; // plot the basin
            mapBasin(basinMap, i - 1, j, isLowestPoint, basinId); // map up
            mapBasin(basinMap, i + 1, j, isLowestPoint, basinId); // map down
            mapBasin(basinMap, i, j - 1, isLowestPoint, basinId); // map left
            mapBasin(basinMap, i, j + 1, isLowestPoint, basinId); // map right
        }
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        boolean[][] isLowestPoint = findLowestPoints();
        int[][] basinMap = new int[isLowestPoint.length][isLowestPoint[0].length];
        int numberOfBasins = 0;

        // map the basins
        for (int i = 0; i < isLowestPoint.length; i++) {
            for (int j = 0; j < isLowestPoint[i].length; j++) {
                if (isLowestPoint[i][j]) {
                    // found a new basin
                    numberOfBasins++;
                    // found a lowest point, so map the basin
                    mapBasin(basinMap, i, j, isLowestPoint, numberOfBasins);
                }
            }
        }

        // now find the size of each basin, using the basin number as an index
        // to the accumulator array
        int[] basinSizes = new int[numberOfBasins];
        for (int i = 0; i < basinMap.length; i++) {
            for (int j = 0; j < basinMap[i].length; j++) {
                if (basinMap[i][j] > 0) {
                    basinSizes[basinMap[i][j] - 1]++;
                }
            }
        }

        // sort the array into ascdending order
        Arrays.sort(basinSizes);

        // return the sum of the three largest sizes
        if (basinSizes.length >= 3) {
            return basinSizes[basinSizes.length - 1]
                    * basinSizes[basinSizes.length - 2]
                    * basinSizes[basinSizes.length - 3];
        } else {
            return -1;
        }
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        // load the input
        ArrayList<String> list = input.collect(Collectors.toCollection(ArrayList<String>::new));

        // size the puzzle
        puzzle = new int[list.size()][list.get(0).length()];

        // break down each string in the list into an array of integers
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                puzzle[i][j] = Integer.parseInt(list.get(i).substring(j, j + 1));
            }
        }
    }
}