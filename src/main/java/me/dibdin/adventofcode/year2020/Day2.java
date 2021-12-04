package me.dibdin.adventofcode.year2020;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Advent of Code Challenge 2020 - Day 2: Password Philosophy.
 * https://adventofcode.com/2020/day/2
 */
public class Day2 extends AbstractChallenge {

    Entry[] puzzle = null;

    /**
     * Helper class used to represent the password entries in the file
     */
    private class Entry {
        private int lower;
        private int upper;
        private char letter;
        private String secret;

        /**
         * Initialise a new object by decoding an input line, for example:
         * 7-10 h: nhhhhhgghphhh
         * @param input the input line
         */
        public Entry(String input) {
            String[] split = input.split("[- :]+");
            this.lower = Integer.parseInt(split[0]);
            this.upper = Integer.parseInt(split[1]);
            this.letter = split[2].charAt(0);
            this.secret = split[3];
        }

        /**
         * Returns the lower number of the entry
         * @return the lower number
         */
        public int getLower() {
            return this.lower;
        }

         /**
         * Returns the upper number of the entry
         * @return the upper number
         */
        public int getUpper() {
            return this.upper;
        }

        /**
         * Returns the letter of the entry
         * @return the letter
         */
        public char getLetter() {
            return this.letter;
        }

                /**
         * Returns the secret password of the entry
         * @return the secret password
         */
        public String getSecret() {
            return this.secret;
        }
    }

    /**
     * Initialise the instance, with the name, year and day of the challege.
     */
    public Day2() {
        super ("Password Philosophy", 2020, 2);
    }

    
    /** 
     * Validates the password using the old rules, based on the number of occurrences
     * of the letter throughout the whole password.
     * @param entry the entry to validate
     * @return boolean returns true if a valid password, otherwise false
     */
    private boolean isSecretValid_Old(Entry entry) {
        int occurrences = 0;

        // converts a string into a char array
        char[] charArray = entry.getSecret().toCharArray();
        
        // count the number of times
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == entry.getLetter()) {
                occurrences++;
            }
        }

        return (entry.getLower() <= occurrences) && (occurrences <= entry.getUpper());
    }

    
    /** 
     * Validates the password using the new rules, based on whether the letter appears at
     * the given locations in the string
     * @param entry the entry to validate
     * @return boolean returns true if a valid password, otherwise false
     */
    private boolean isSecretValid_New(Entry entry) {
        char[] charArray = entry.getSecret().toCharArray();
        int occurrences = 0;

        // does the letter appear at the first location
        if (charArray[entry.getLower() - 1] == entry.getLetter()) { 
            occurrences++; 
        }

        // does the letter appear at the second location
        if (charArray[entry.getUpper() - 1] == entry.getLetter()) {
            occurrences++;
        }

        // return true if there has been just one occurance of the letter
        return (occurrences == 1);
    }
    
    /**
     * Solve part one of the puzzle. 
     * @return long the number of valid entries
     */
    public long solvePartOne() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        int count = 0;

        // count how many valid entries there are.
        for (int i = 0; i < puzzle.length; i++) {
            if (isSecretValid_Old(puzzle[i])) {
                count++;
            }
        }
        
        return count;
    }

    
    /**
     * Solve part two of the puzzle. 
     * @return long the number of valid entries
     */
    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }
        
        int count = 0;

        // count how many valid entries there are.
        for (int i = 0; i < puzzle.length; i++) {
            if (isSecretValid_New(puzzle[i])) {
                count++;
            }
        }

        return count;
    }

    
    /**
     * Solve both parts of hte puzzle for the given input. 
     * @param input a stream of strings to process as the puzzle input
     */
    public void setPuzzleInput(Stream<String> input) {
        
        // Convert the input into an array of Entries
        ArrayList<Entry> list = input.map(str -> new Entry(str)).collect(Collectors.toCollection(ArrayList<Entry>::new));
        puzzle = list.toArray(new Entry[list.size()]);
    }
}