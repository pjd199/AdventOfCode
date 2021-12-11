package me.dibdin.adventofcode.year2021;

import static java.util.Map.entry;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 10: Syntax Scoring.
 * https://adventofcode.com/2021/day/10
 */
public class Day10 extends AbstractChallenge {

    ArrayList<String> puzzle = null;

    /**
     * Constructor.
     */
    public Day10() {
        super("Syntax Scoring", 2021, 10);
    }

    /**
     * Returns true is the character is an opening bracket
     * 
     * @param c the character to test
     * @return true is the character is (, [, {, or <
     */
    private boolean isOpeningBacket(char c) {
        return (c == '(') || (c == '[') || (c == '{') || (c == '<');
    }

    /**
     * Returns true is the two arguments are a matching bracket pair
     * 
     * @param opening the first character
     * @param closing the second character
     * @return true if the input is ( ), [ ], { } or < >
     */
    private boolean isMatchingPair(char opening, char closing) {
        return ((opening == '(') && (closing == ')')) ||
                ((opening == '[') && (closing == ']')) ||
                ((opening == '{') && (closing == '}')) ||
                ((opening == '<') && (closing == '>'));
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        // create a mapping for the scores
        Map<Character, Integer> scoreMap = Map.ofEntries(
                entry(')', 3),
                entry(']', 57),
                entry('}', 1197),
                entry('>', 25137));

        // the running total
        long total = 0;

        for (String line : puzzle) {
            // we'll be using a stack to track the opening and closing brackets
            ArrayDeque<Character> stack = new ArrayDeque<Character>();

            for (char bracket : line.toCharArray()) {
                if (isOpeningBacket(bracket)) {
                    // add to the stack
                    stack.addFirst(bracket);
                } else if (!isMatchingPair(stack.removeFirst(), bracket)) {
                    // c is wrong - the line is corrupted
                    total += scoreMap.get(bracket);
                    break;
                }
            }
        }

        return total;
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        // create a mapping for the scores
        Map<Character, Integer> scoreMap = Map.ofEntries(
                entry('(', 1),
                entry('[', 2),
                entry('{', 3),
                entry('<', 4));

        // Scores will be recorded in a list so we can return the median value
        ArrayList<Long> totals = new ArrayList<Long>();

        for (String line : puzzle) {

            ArrayDeque<Character> stack = new ArrayDeque<Character>();
            boolean lineCorrupted = false;

            for (char bracket : line.toCharArray()) {
                if (isOpeningBacket(bracket)) {
                    // add to the stack
                    stack.addFirst(bracket);
                } else if (!isMatchingPair(stack.removeFirst(), bracket)) {
                    // c is wrong, line is corrupted
                    lineCorrupted = true;
                    break;
                }
            }

            if (!lineCorrupted) {
                // score points as we complete the line
                long score = 0;
                while (stack.size() > 0) {
                    score = (score * 5) + scoreMap.get(stack.removeFirst());
                }
                totals.add(score);
            }
        }

        // return the median value. Note, there will always be an odd number of input lines
        Collections.sort(totals);
        return totals.get(totals.size() / 2);
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        puzzle = input.collect(Collectors.toCollection(ArrayList<String>::new));
    }
}