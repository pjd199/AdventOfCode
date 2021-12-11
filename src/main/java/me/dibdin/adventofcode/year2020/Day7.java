package me.dibdin.adventofcode.year2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2020 - Day 7: Handy Haversacks.
 * https://adventofcode.com/2020/day/7
 */
public class Day7 extends AbstractChallenge {

    HashMap<String, Bag> puzzle = null;

    /**
     * Inner class to represent a bag from the input file
     */
    public class Bag {
        private ArrayList<String> innerBagColors = new ArrayList<String>();
        private String color = null;

        /**
         * Construct a new Bag by decoding the input line
         * 
         * @param description the input line to decode
         */
        public Bag(String description) {
            Iterator<String> iterator = Arrays.asList(description.split(" ")).iterator();

            color = iterator.next() + " " + iterator.next();
            iterator.next(); // skip "bags"
            iterator.next(); // skip "contain"

            while (iterator.hasNext()) {
                String number = iterator.next();
                if (number.equals("no")) {
                    break;
                } else {
                    String innerColor = iterator.next() + " " + iterator.next();
                    iterator.next(); // skip "bags"
                    for (int i = 0; i < Integer.parseInt(number); i++) {
                        innerBagColors.add(innerColor);
                    }
                }
            }
        }

        /**
         * Return the full list of bags by color, including multiples
         * 
         * @return an array of bags
         */
        public String[] getInnerBagColors() {
            return innerBagColors.toArray(new String[innerBagColors.size()]);
        }

        /**
         * Return a set view of bag colors
         * 
         * @return an array of bags
         */
        public String[] getInnerBagColorsSet() {
            HashSet<String> set = new HashSet<String>(innerBagColors);
            return set.toArray(new String[set.size()]);
        }

        /**
         * The color of the bag
         * 
         * @return the color of the bag
         */
        public String getColor() {
            return color;
        }
    }

    /**
     * Initialise the instance, with the name, year and day of the challege.
     */
    public Day7() {
        super("Handy Haversacks", 2020, 7);
    }

    /**
     * Recusrive method which returns true if this bag or it's inner bags
     * contain a gold bag
     * 
     * @param bag the bag to search
     * @return true if a gold bag is found
     */
    private boolean containsGoldBag(String bagColor) {
        Bag bag = puzzle.get(bagColor);
        String[] bagsColors = bag.getInnerBagColorsSet();

        for (int i = 0; i < bagsColors.length; i++) {
            if (bagsColors[i].equals("shiny gold") || containsGoldBag(bagsColors[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Solves part one
     * 
     * @return the result.
     */
    public long solvePartOne() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        long total = 0;
        for (Bag bag : puzzle.values()) {
            if (!bag.getColor().equals("shiny gold") && containsGoldBag(bag.getColor())) {
                total++;
            }
        }

        return total;
    }

    /**
     * Recusively creates a List from all the bags within the bag
     * 
     * @param bagColor the color of the bag to unpack
     * @return the List
     */
    public List<String> unpackBag(String bagColor) {
        Bag bag = puzzle.get(bagColor);

        // add these bags to the return list
        String[] innerBagColors = bag.getInnerBagColors();
        ArrayList<String> bags = new ArrayList<String>(Arrays.asList(innerBagColors));

        // add all the bags within each of the inner bags
        for (String color : innerBagColors) {
            bags.addAll(unpackBag(color));
        }

        return bags;
    }

    /**
     * Solves part two
     * 
     * @return the result.
     */
    public long solvePartTwo() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        List<String> bags = unpackBag("shiny gold");

        return bags.size();
    }

    /**
     * Sets the puzzle input
     * 
     * @param input a stream of strings to process as the puzzle input
     */
    public void setPuzzleInput(Stream<String> input) {
        ArrayList<Bag> bagList = input.map(x -> new Bag(x)).collect(Collectors.toCollection(ArrayList<Bag>::new));

        puzzle = new HashMap<String, Bag>();
        for (Bag bag : bagList) {
            puzzle.put(bag.getColor(), bag);
        }
    }
}