package me.dibdin.adventofcode.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 20: Trench Map.
 * https://adventofcode.com/2021/day/20
 */
public class Day20 extends AbstractChallenge {

    // puzzle data
    char[] enhancement = null;
    char[][] puzzle = null;
    boolean readyToSolve = false;

    // char representations of light and dark pixels
    final char LIGHT_PIXEL = '#';
    final char DARK_PIXEL = '.';

    /**
     * Constructor.
     */
    public Day20() {
        super("Trench Map", 2021, 20);
    }

    /**
     * If the i and j indexes are valid for array, return true if the array[i][j] is equal
     * to match. If the i and j indexes are out of bounds for the array, return true if
     * infinite is equal to match.
     * 
     * @param array the array for the loop up
     * @param i the primary index of the two dimensional array
     * @param j the secondary index of the two dimensional array
     * @param match the char to match to either the array index or the infinite char
     * @param infinite the infinite char to compare if the array if index out of bounds
     * @return true if a match if found, otherwise false
     */
    private boolean match(char[][] array, int i, int j, char match, char infinite) {
        if ((i >= 0) && (i < array.length) && (j >= 0) && (j < array[i].length)) {
            return array[i][j] == match;
        } else {
            return infinite == match;
        }
    }

    /**
     * Solve the puzzle by running image enhancement algorithm for a given number of times
     * @param rounds how many times to run the enhancement algorithm
     * @return the count of the number of light pixels at the end of the enhancement
     */
    private int solve(int rounds) {

        // initial setup for the enhancement algorithm
        char[][] imageArray = Arrays.copyOf(puzzle, puzzle.length);
        char infinite = DARK_PIXEL;
        StringBuilder sb = new StringBuilder("123456789");

        // repeat for the request number of rounds
        while (rounds > 0) {
            rounds--;

            // array for the results of this round, adding padding on all sides 
            char[][] processedImage = new char[imageArray.length + 2][imageArray[0].length + 2];

            // enhannce the image, place the results in processedImage
            // image array is centered over processed image
            for (int i = 0; i < processedImage.length; i++) {
                for (int j = 0; j < processedImage[i].length; j++) {
                    // top row
                    sb.setCharAt(0, match(imageArray, i - 2, j - 2, LIGHT_PIXEL, infinite) ? '1' : '0');
                    sb.setCharAt(1, match(imageArray, i - 2, j - 1, LIGHT_PIXEL, infinite) ? '1' : '0');
                    sb.setCharAt(2, match(imageArray, i - 2, j, LIGHT_PIXEL, infinite) ? '1' : '0');
                    // middle row
                    sb.setCharAt(3, match(imageArray, i - 1, j - 2, LIGHT_PIXEL, infinite) ? '1' : '0');
                    sb.setCharAt(4, match(imageArray, i - 1, j - 1, LIGHT_PIXEL, infinite) ? '1' : '0');
                    sb.setCharAt(5, match(imageArray, i - 1, j, LIGHT_PIXEL, infinite) ? '1' : '0');
                    // bottom row
                    sb.setCharAt(6, match(imageArray, i, j - 2, LIGHT_PIXEL, infinite) ? '1' : '0');
                    sb.setCharAt(7, match(imageArray, i, j - 1, LIGHT_PIXEL, infinite) ? '1' : '0');
                    sb.setCharAt(8, match(imageArray, i, j, LIGHT_PIXEL, infinite) ? '1' : '0');

                    processedImage[i][j] = enhancement[Integer.valueOf(sb.toString(), 2)];
                }
            }

            // prepare for the next round
            imageArray = processedImage;
            infinite = (infinite == LIGHT_PIXEL) ? enhancement[511] : enhancement[0];
        }

        // count the number of LIGHT_PIXELs
        int count = 0;
        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                if (imageArray[i][j] == LIGHT_PIXEL) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        return solve(2);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        return solve(50);
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        Iterator<String> iterator = input.iterator();

        // read the enhancement algorithm line 
        enhancement = iterator.next().toCharArray();

        // load up the puzzle input
        ArrayList<char[]> list = new ArrayList<char[]>();
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (!line.equals("")) {
                list.add(line.toCharArray());
            }
        }

        puzzle = list.toArray(new char[list.size()][]);

        readyToSolve = true;
    }
}