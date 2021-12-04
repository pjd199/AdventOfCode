package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.IllegalStateException;

/**
 * Advent of Code Challenge 2021 - Day 3: Binary Diagnostic.
 * https://adventofcode.com/2021/day/3
 */
public class Day3 extends AbstractChallenge {

    ArrayList<String> puzzle = null;

    /**
     * Constructor.
     */
    public Day3() {
        super ("Binary Diagnostic", 2021, 3);
    }

    /**
     * Finds the most common value at the specified location
     * @param list list of Strings to search through
     * @param position the position of the char in the string
     * @return the most common char
     */
    private char mostCommonValueAt(List<String> list, int position) {
        int zeros = 0;
        int ones = 0;
        for (String number : list) {
            switch(number.charAt(position)) {
                case '0':
                    zeros++;
                    break;
                case '1':
                    ones++;
                    break;
            }
        }

        if (ones >= zeros) {
            return '1';
        } else {
            return '0';
        }
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        String gamma = "";
        String epsilon = "";

        for (int bit = 0; bit < puzzle.get(0).length(); bit++) {
            char mostCommon = mostCommonValueAt(puzzle, bit);
            if (mostCommon == '1') {
                gamma   += '1';
                epsilon += '0';
            } else {
                gamma   += '0';
                epsilon += '1';
            }
        }

        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }
        String oxygen;
        String scrubber;
        int i;
        ArrayList<String> list;

        // oxygen is the found using the most common values
        i = 0;
        list = new ArrayList<String>(puzzle);
        while ((list.size() > 1) && (i < list.get(0).length())) {
            final int position = i;
            char mostCommon = mostCommonValueAt(list, i);
            list.removeIf(x -> (x.charAt(position) == mostCommon));
            i++;
        }
        oxygen = list.get(0);

        // scrubber is found using the least common values
        i = 0;
        list = new ArrayList<String>(puzzle);
        while ((list.size() > 1) && (i < list.get(0).length())) {
            final int position = i;
            char leastCommon = mostCommonValueAt(list, i) == '1' ? '0' : '1';
            list.removeIf(x -> (x.charAt(position) == leastCommon));
            i++;
        }
        scrubber = list.get(0);
        
        return Integer.parseInt(oxygen, 2) * Integer.parseInt(scrubber, 2);
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        puzzle = input.collect(Collectors.toCollection(ArrayList<String>::new));
    }
}