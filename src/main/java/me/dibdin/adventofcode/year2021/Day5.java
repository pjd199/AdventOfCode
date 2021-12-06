package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.ArrayList;
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

    private class Line {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        private Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public int getX1() {
            return this.x1;
        }

        public int getY1() {
            return this.y1;
        }

        public int getX2() {
            return this.x2;
        }

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

    private int[][] newGrid() {
        // find the number of rows and cols in the puzzle
        int cols = 0;
        int rows = 0;
        for (Line line : puzzle) {
            cols = Integer.max(Integer.max(cols, line.getX1()), line.getX2());
            rows = Integer.max(Integer.max(rows, line.getY1()), line.getY2());
        }

        //create the grid
        return new int[rows + 1][cols + 1]; // default values are zero
    }

    private void mapIfHorizontal(int grid[][], Line line) {
        // map if a horizontal lines (ie y1 == y2)
        if (line.getY1() == line.getY2()) {
            int colStart = Integer.min(line.getX1(), line.getX2());
            int colEnd = Integer.max(line.getX1(), line.getX2());
            for (int i = colStart; i <= colEnd; i++) {
                grid[line.getY1()][i]++;
            }
        }
    }

    private void mapIfVertical(int grid[][], Line line) {
        // map if a vertical line (ie y1 == y2)
        if (line.getX1() == line.getX2()) {
            int rowStart = Integer.min(line.getY1(), line.getY2());
            int rowEnd = Integer.max(line.getY1(), line.getY2());
            for (int i = rowStart; i <= rowEnd; i++) {
                grid[i][line.getX1()]++;
            }
        }
    }

    private void mapIfDiagonal(int grid[][], Line line) {
        if (!(line.getX1() == line.getX2()) && !(line.getY1() == line.getY2())) {
            int x = line.getX1();
            int y = line.getY1();
            while (x != line.getX2() && y != line.getY2()) {
                grid[y][x]++;
                x = (x < line.getX2()) ? (x + 1) : (x - 1);
                y = (y < line.getY2()) ? (y + 1) : (y - 1);
            }
            grid[line.getY2()][line.getX2()]++;
        }
    }

    private long countOverlaps(int grid[][]) {
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

        int[][] grid = newGrid();

        // map the lines into the grid
        for (Line line : puzzle) {
            mapIfHorizontal(grid, line);
            mapIfVertical(grid, line);
        }

        // find the number of overlaps
        return countOverlaps(grid);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        int[][] grid = newGrid();

        // map the lines into the grid
        for (Line line : puzzle) {
            mapIfHorizontal(grid, line);
            mapIfVertical(grid, line);
            mapIfDiagonal(grid, line);
        }

        // find the number of overlaps
        return countOverlaps(grid);
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
 
        //322,257 -> 157,422
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

    }
}