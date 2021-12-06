package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.IllegalStateException;

/**
 * Advent of Code Challenge 2021 - Day 5: Hydrothermal Venture.
 * https://adventofcode.com/2021/day/5
 */
public class Day5 extends AbstractChallenge {

    ArrayList<Line> puzzle = null;
    int [][] grid = null;

    /**
     * Helper class to represent a line
     */
    private class Line {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        /**
         * Construct a new line object
         * @param x1 the X co-ordinate at the start of the line
         * @param y1 the Y co-ordinate at the start of the line
         * @param x2 the X co-ordinate at the end of the line
         * @param y2 the Y co-ordinate at the end` of the line
         */
        private Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        /**
         * Get the X co-ordinate at the start of the line
         * @return the X co-ordinate
         */
        public int getX1() {
            return this.x1;
        }

        /**
         * Get the Y co-ordinate at the start of the line
         * @return the Y co-ordinate
         */
        public int getY1() {
            return this.y1;
        }

        /**
         * Get the X co-ordinate at the end of the line
         * @return the X co-ordinate
         */
        public int getX2() {
            return this.x2;
        }

        /**
         * Get the Y co-ordinate at the end of the line
         * @return the Y co-ordinate
         */
        public int getY2() {
            return this.y2;
        }
    } 

    /**
     * Constructor.
     */
    public Day5() {
        super ("Hydrothermal Venture", 2021, 5);
    }

    /**
     * Map a horizonal line
     * @param line the line to map
     */
    private void mapHorizontalLine(Line line) {
        int x = line.getX1();
        while (x != line.getX2()) {
            grid[line.getY1()][x]++;
            x = (x < line.getX2()) ? (x + 1) : (x - 1);
        }
        grid[line.getY2()][line.getX2()]++; // add the final point
    }


    /**
     * Map a vertical line
     * @param line the line to map
     */
    private void mapVerticalLine(Line line) {
        int y = line.getY1();
        while (y != line.getY2()) {
            grid[y][line.getX1()]++;
            y = (y < line.getY2()) ? (y + 1) : (y - 1);
        }
        grid[line.getY2()][line.getX2()]++; // add the final point
    }

    /**
     * Map a diagonal line
     * @param line the line to map
     */
    private void mapDiagonalLine(Line line) {
        int x = line.getX1();
        int y = line.getY1();
        while (x != line.getX2() && y != line.getY2()) {
            grid[y][x]++;
            x = (x < line.getX2()) ? (x + 1) : (x - 1);
            y = (y < line.getY2()) ? (y + 1) : (y - 1);
        }
        grid[line.getY2()][line.getX2()]++; // add the final point
    }

    /**
     * Count how map times the lines cross on the map
     * @return the count
     */
    private long countOverlaps() {
        int result = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] > 1) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        // clear the grid
        for (int[] row : grid) {
            Arrays.fill(row, 0);
        }

        // map the lines into the grid
        for (Line line : puzzle) {
            if (line.getY1() == line.getY2()) {
                mapHorizontalLine(line);
            } else if (line.getX1() == line.getX2()) {
                mapVerticalLine(line);
            }
        }

        // find the number of overlaps
        return countOverlaps();
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        // clear the grid
        for (int[] row : grid) {
            Arrays.fill(row, 0);
        }

        // map the lines into the grid
        for (Line line : puzzle) {
            if (line.getY1() == line.getY2()) {
                mapHorizontalLine(line);
            } else if (line.getX1() == line.getX2()) {
                mapVerticalLine(line);
            } else {
                mapDiagonalLine(line);
            }
        }

        // find the number of overlaps
        return countOverlaps();
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
 
        // decode the input stream into Lines of the puzzle
        Pattern pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

        puzzle = input.map(str -> {
            final Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return new Line(Integer.parseInt(matcher.group(1)),
                                Integer.parseInt(matcher.group(2)),
                                Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(4)));
            } else {
                return null;
            }
        }).filter(x -> x != null).collect(Collectors.toCollection(ArrayList<Line>::new)); 

        //create the grid
        int cols = 0;
        int rows = 0;
        for (Line line : puzzle) {
            cols = Integer.max(Integer.max(cols, line.getX1()), line.getX2());
            rows = Integer.max(Integer.max(rows, line.getY1()), line.getY2());
        }
        grid = new int[rows + 1][cols + 1];
    }
}