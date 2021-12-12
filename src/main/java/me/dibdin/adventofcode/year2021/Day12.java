package me.dibdin.adventofcode.year2021;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 12: Passage Pathing.
 * https://adventofcode.com/2021/day/12
 */
public class Day12 extends AbstractChallenge {

    HashMap<String, ArrayList<String>> puzzle = null;

    /**
     * Constructor.
     */
    public Day12() {
        super("Passage Pathing", 2021, 12);
    }

    /**
     * Map all the routes in the cave system
     * 
     * @param cave        the cave we're currently in
     * @param breadcrumbs the breadcrumbs of the route we've taken to get here
     * @param validRoutes the list of valid routes
     */
    private void mapRoutes(String cave, ArrayDeque<String> breadcrumbs, ArrayList<String> validRoutes,
            boolean canVisitASingleSmallCaveTwice) {
        // add the cave to the breadcrumbs trail
        breadcrumbs.addLast(cave);

        // list of paths out of this cave
        ArrayList<String> pathways = puzzle.get(cave);

        for (String path : pathways) {
            if (path.equals("start")) {
                // don't go back to where we started
            } else if (path.equals("end")) {
                // found the end - record the route
                validRoutes.add(String.join(",", breadcrumbs) + "," + path);
            } else if (breadcrumbs.contains(path) && path.toLowerCase().equals(path)) {
                // this is a small cave we've already been to, are we allowed to continue
                if (canVisitASingleSmallCaveTwice) {
                    // we're allowed to visit a single small cave twice
                    mapRoutes(path, breadcrumbs, validRoutes, false);
                }
            } else {
                // keep exploring
                mapRoutes(path, breadcrumbs, validRoutes, canVisitASingleSmallCaveTwice);
            }
        }

        // remove cave from breadcrumbs as we return
        breadcrumbs.removeLast();
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        // use a stack to record the breadcrumbs of the route through the caves
        ArrayDeque<String> breadcrumbs = new ArrayDeque<String>();

        // will become a list of valid routs
        ArrayList<String> validRoutes = new ArrayList<String>();

        // map all the routes, begining at the start
        mapRoutes("start", breadcrumbs, validRoutes, false);

        // return the number of routes found
        return validRoutes.size();
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) {
            throw new IllegalStateException("No puzzle input set");
        }

        // use a stack to record the breadcrumbs of the route through the caves
        ArrayDeque<String> breadcrumbs = new ArrayDeque<String>();

        // will become a list of valid routs
        ArrayList<String> validRoutes = new ArrayList<String>();

        // map all the routes, begining at the start
        mapRoutes("start", breadcrumbs, validRoutes, true);

        // return the number of routes found
        return validRoutes.size();
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        // puzzle will be a map of cave names, with a list of routes out of the cave
        puzzle = new HashMap<String, ArrayList<String>>();

        // loop through all the input lines
        Iterator<String> iterator = input.iterator();
        while (iterator.hasNext()) {
            String caves[] = iterator.next().split("-");

            // add caves to the puzzle, if they don't all ready exist
            for (String cave : caves) {
                if (puzzle.get(cave) == null) {
                    puzzle.put(cave, new ArrayList<String>());
                }
            }
            // add the route to the caves
            puzzle.get(caves[0]).add(caves[1]);
            puzzle.get(caves[1]).add(caves[0]);
        }
    }
}