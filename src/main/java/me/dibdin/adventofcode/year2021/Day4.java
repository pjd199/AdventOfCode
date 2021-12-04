package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;
import java.lang.IllegalStateException;

/**
 * Advent of Code Challenge 2021 - Day 4: Giant Squid.
 * https://adventofcode.com/2021/day/4
 */
public class Day4 extends AbstractChallenge {

    // how many rows and cols for each grid
    private final int rows = 5;
    private final int cols = 5;

    int[] caller = null;
    ArrayList<Board> boards = null;

    /**
     * Inner class to represent a Bingo board
     */
    public class Board {
        private int[][] grid = new int[rows][cols];
        private boolean[][] mark = new boolean[rows][cols];

        /**
         * Add a row of numbers to the board
         * @param rowIndex the row number which is being added.
         * @param row the numbers in the rol
         */
        public void addrow(int rowIndex, int[] row) {
            if ((rowIndex >= 0 ) && (rowIndex < rows) && (row.length == cols)) {
                grid[rowIndex] = row;
            } 
        }

        /**
         * Mark the specified number in baord
         * @param number the number to mark as calls
         */
        public void mark(int number) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (grid[row][col] == number) {
                        mark[row][col] = true;
                    }
                }
            }
        }

        /**
         * Reset the marks on the board
         */
        public void clearAllMarks() {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    mark[row][col] = false;
                }
            }
        }

        /**
         * Does this board had a marked line?
         * @return true if there is a completely marked row or column
         */
        public boolean isBingo() {
            // check each row for marks in each column
            for (int row = 0; row < rows; row++) {
                boolean allInRow = true;
                for (int col = 0; col < cols; col++) {
                    allInRow &= mark[row][col];
                }
                if (allInRow) {
                    return true;
                }
            }

            // check each col for marks in each row
            for (int col = 0; col < cols; col++) {
                boolean allInCol = true;
                for (int row = 0; row < rows; row++) {
                    allInCol &= mark[row][col];
                }
                if (allInCol) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Add up all the numbers which have not been marked
         * @return the sum of the unmarked numbers
         */
        public int getSumOfUnmarkedNumbers() {
            int sum = 0;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (!mark[row][col]) {
                        sum += grid[row][col];
                    }
                }
            }
            return sum;
        }
    }

    /**
     * Constructor.
     */
    public Day4() {
        super ("Giant Squid", 2021, 4);
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (boards == null || caller == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        // clear all the marks on the boards
        for (Board board : boards) {
            board.clearAllMarks();
        }

        // for each number in the caller array
        for (int i = 0; i < caller.length; i++) {
            //mark in each of the boards
            for (Board board : boards) {
                board.mark(caller[i]);
                if (board.isBingo()) {
                    return board.getSumOfUnmarkedNumbers() * caller[i];
                }
            }
        }

        return -1;
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (boards == null || caller == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        // clear all the marks on the board
        for (Board board : boards) {
            board.clearAllMarks();
        }

        // create a list of all boards in play
        List<Board> boardsinPlay = new ArrayList<Board>(boards);

        // for each number in the caller array
        for (int i = 0; i < caller.length; i++) {
            //mark in each of the boards
            int board = 0;
            while (board < boardsinPlay.size()) {
                // mark the number on the board
                boardsinPlay.get(board).mark(caller[i]);
                if (boardsinPlay.get(board).isBingo()) {
                    // Bingo - so remove this board from play, unless it's the last one
                    if (boardsinPlay.size() > 1) {
                        boardsinPlay.remove(board);
                    } else {
                        return boardsinPlay.get(board).getSumOfUnmarkedNumbers() * caller[i];
                    }
                } else {
                    // move to the next one
                    board++;
                }
            }
        }

        return -1;
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        // iterate through the input, line by line
        Iterator<String> lines = input.iterator();

        // convert the first line into an array of strings
        caller = Stream.of(lines.next().split(",")).mapToInt(Integer::parseInt).toArray();
        lines.next(); // skip the blank line

        // start reading the boards
        boards = new ArrayList<Board>();
        Board board = new Board();
        int row = 0; 
        while (lines.hasNext()) {
            String line = lines.next();
            if (line.equals("")) {
                // end of the board
                boards.add(board);
                board = new Board();
                row = 0;
            } else {
                // add a row to the board
                board.addrow(row, Stream.of(line.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray());
                row++;
            }
        }
        // add the last board
        boards.add(board);
    }
}