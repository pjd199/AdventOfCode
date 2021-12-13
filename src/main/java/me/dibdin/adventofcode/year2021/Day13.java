package me.dibdin.adventofcode.year2021;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 13: Transparent Origami.
 * https://adventofcode.com/2021/day/13
 */
public class Day13 extends AbstractChallenge {

    HashSet<Point> points = null;
    ArrayList<String> instructions = null;

    /**
     * Constructor.
     */
    public Day13() {
        super("Transparent Origami", 2021, 13);
    }

    /**
     * Fold the input using the instruction
     * @param input the collection of points to fold
     * @param instruction the folding instruction
     * @return a new set containing the folded points
     */
    private HashSet<Point> foldPaper(Set<Point> input, String instruction) {

        final int foldAt = Integer.parseInt(instruction.split("=")[1]);

        if (instruction.contains("y=")) {
            // fold the paper up
            return input.stream()
                    .map((Point point) -> {
                        if (point.y > foldAt) {
                            return new Point(point.x, foldAt - (point.y - foldAt));
                        } else {
                            return point;
                        }
                    }).collect(Collectors.toCollection(HashSet<Point>::new));
        } else {
            // fold the paper left
            return input.stream()
                    .map((Point point) -> {
                        if (point.x > foldAt) {
                            return new Point(foldAt - (point.x - foldAt), point.y);
                        } else {
                            return point;
                        }
                    }).collect(Collectors.toCollection(HashSet<Point>::new));
        }
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() throws IllegalStateException {
        if ((points == null) || (instructions == null)) {
            throw new IllegalStateException("No puzzle input set");
        }

        // fold the paper and return the number of points in the set
        return foldPaper(points, instructions.get(0)).size();
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() throws IllegalStateException {
        if ((points == null) || (instructions == null)) {
            throw new IllegalStateException("No puzzle input set");
        }

        // add the points to the paper
        HashSet<Point> paper = new HashSet<Point>(points);

        // complete all the fold instructions
        for (String instruction : instructions) {
            paper = foldPaper(paper, instruction);
        }

        // find the max X and Y values so we can make an array
        int maxX = paper.stream().mapToInt(point -> point.x).max().getAsInt();
        int maxY = paper.stream().mapToInt(point -> point.y).max().getAsInt();

        // map the dots into an array
        boolean[][] dots = new boolean[maxY + 1][maxX + 1];
        for (Point point : paper) {
            dots[point.y][point.x] = true;
        }

        // print the array
        System.out.println(getName() + " ASCII Art");
        for (int y = 0; y < dots.length; y++) {
            StringBuilder sb = new StringBuilder(y + ": ");
            for (int x = 0; x < dots[y].length; x++) {
                sb.append(dots[y][x] ? "#" : ".");
            }
            System.out.println(sb);
        }
        System.out.println("");

        // output is the printed letters in ASCII art, rather the a number
        return -1;
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        // Initialise the data structures
        points = new HashSet<Point>();
        instructions = new ArrayList<String>();

        // Iterate through the lines
        Iterator<String> iterator = input.iterator();
        while (iterator.hasNext()) {
            String line = iterator.next();

            if (line.contains(",")) {
                // Found a point in form "x,y"
                String[] tokens = line.split(",");
                points.add(new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));

            } else if (line.contains("fold along")) {
                // Found a folding instruction
                instructions.add(line);
            }
        }
    }
}