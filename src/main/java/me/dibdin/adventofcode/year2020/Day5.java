package me.dibdin.adventofcode.year2020;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Advent of Code Challenge 2020 - Day 5: Binary Boarding.
 * https://adventofcode.com/2020/day/5
 */
public class Day5 extends AbstractChallenge {

    class Seat implements Comparable<Seat> {
        private int row;
        private int column;
        private int seatId;

        public Seat(String code) {
            code = code.replace('B', '1')
                       .replace('F', '0')
                       .replace('L', '0')
                       .replace('R', '1');

            row = Integer.parseUnsignedInt(code, 0, 7, 2);
            column = Integer.parseUnsignedInt(code, 7, 10, 2);
            seatId = (row * 8) + column;
        }

        public int compareTo(Seat other) {
            return this.seatId - other.seatId;
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }

        public int getSeatId() {
            return this.seatId;
        }
    }

    /**
     * Initialise the instance, with the name, year and day of the challege.
     */
    public Day5() {
        super ("Binary Boarding", 2020, 5);
    }

    private int solvePartOne(Seat[] input) {
        return input[input.length - 1].getSeatId();
    }

    private int solvePartTwo(Seat[] input) {
        for (int i = 0; i < (input.length - 1); i++) {
            if ((input[i].getSeatId() + 1) == (input[i+1].getSeatId() - 1)) {
                    return input[i].getSeatId() + 1;
            }
        }
        return -1;
    }
    
    /**
     * Solve both parts of the puzzle for the given input. 
     * @param input a stream of strings to process as the puzzle input
     */
    public void solve(Stream<String> input) {

        ArrayList<Seat> seatList = input.map(str -> new Seat(str))
                                     .sorted()
                                     .collect(Collectors.toCollection(ArrayList<Seat>::new));
        Seat[] seats = seatList.toArray(new Seat[seatList.size()]);

        // Solve the puzzles
        setPartOneResult(Integer.toString(solvePartOne(seats)));
        setPartTwoResult(Integer.toString(solvePartTwo(seats)));
    }
}