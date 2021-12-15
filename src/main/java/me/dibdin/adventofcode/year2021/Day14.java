package me.dibdin.adventofcode.year2021;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 14: Extended Polymerization.
 * https://adventofcode.com/2021/day/14
 */
public class Day14 extends AbstractChallenge {

    String polymerTemplate = null;
    HashMap<String, Character> pairInsertionRules = null;
    boolean isInputSet = false;

    /**
     * Constructor.
     */
    public Day14() {
        super("Extended Polymerization", 2021, 14);
    }

    /**
     * Increase the existing value stored under key in the map by value
     * 
     * @param map   the map the use
     * @param key   the key that stores the existing value
     * @param value the value to increament by
     */
    private void increamentValueInMap(Map<String, Long> map, String key, Long value) {
        Long storedValue = map.get(key);
        if (storedValue == null) {
            map.put(key, value);
        } else {
            map.put(key, storedValue + value);
        }
    }

    /**
     * Run the polymerization cycles
     * 
     * @param cycles how many cycles to run for
     * @return a mapping of the letters to the number of occurances after the
     *         process
     */
    private Map<String, Long> polymerization(int cycles) {
        HashMap<String, Long> pairs = new HashMap<String, Long>();
        HashMap<String, Long> counters = new HashMap<String, Long>();

        // add the pairs to the pool
        for (int i = 0; i < (polymerTemplate.length() - 1); i++) {
            increamentValueInMap(pairs, polymerTemplate.substring(i, i + 2), 1L);
        }

        // add the polyerTemplate letters to the counter
        for (int i = 0; i < polymerTemplate.length(); i++) {
            increamentValueInMap(counters, polymerTemplate.substring(i, i + 1), 1L);
        }

        // run the sequence
        for (int j = 0; j < cycles; j++) {

            // for each pair in the pool
            HashMap<String, Long> newPairs = new HashMap<String, Long>();
            for (String pair : pairs.keySet()) {

                // take this pair from the pool
                Long numberOfPairs = pairs.get(pair);

                // split the pair, polyermize with the inserted element, then put both pairs in
                // the pool
                Character insertElement = pairInsertionRules.get(pair);
                increamentValueInMap(newPairs, pair.substring(0, 1) + insertElement, numberOfPairs);
                increamentValueInMap(newPairs, insertElement + pair.substring(1, 2), numberOfPairs);

                // increase the counters with the number of insertedElements
                increamentValueInMap(counters, String.valueOf(insertElement), numberOfPairs);
            }
            // get ready for the next cycle
            pairs = newPairs;
        }

        // end of polymerization
        return counters;
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (!isInputSet) {
            throw new IllegalStateException("No puzzle input set");
        }

        // Run the polymerisation for 40 cycles
        Map<String, Long> counters = polymerization(10);

        // Find the min and max
        long min = counters.values().stream().mapToLong(Long::valueOf).min().getAsLong();
        long max = counters.values().stream().mapToLong(Long::valueOf).max().getAsLong();

        // Return the differece between min and max
        return max - min;
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (!isInputSet) {
            throw new IllegalStateException("No puzzle input set");
        }

        // Run the polymerisation for 40 cycles
        Map<String, Long> counters = polymerization(40);

        // Find the min and max
        long min = counters.values().stream().mapToLong(Long::valueOf).min().getAsLong();
        long max = counters.values().stream().mapToLong(Long::valueOf).max().getAsLong();

        // Return the differece between min and max
        return max - min;
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        Iterator<String> iterator = input.iterator();

        // store the polymer template
        polymerTemplate = iterator.next();

        // store the pairInsertionRules
        pairInsertionRules = new HashMap<String, Character>();
        while (iterator.hasNext()) {
            String[] tokens = iterator.next().split(" -> ");
            if (tokens.length == 2) {
                pairInsertionRules.put(tokens[0], tokens[1].charAt(0));
            }
        }
        isInputSet = true;
    }
}