package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.IllegalStateException;

/**
 * Advent of Code Challenge 2021 - Day 2: Dive!.
 * https://adventofcode.com/2021/day/2
 */
public class Day2 extends AbstractChallenge {

    Command[] puzzle = null;

    /**
     * Class representing the commands read from the input
     */
    public class Command {
        final static int FORWARD = 0;
        final static int DOWN = 1;
        final static int UP = 2;

        private int command;
        private int unit;

        public Command(String input) {
            String[] words = input.split(" ");
            switch (words[0]) {
                case "forward":
                    command = Command.FORWARD;
                    break;
                case "down":
                    command = Command.DOWN;
                    break;
                case "up":
                    command = Command.UP;
                    break;
            }
            unit = Integer.parseInt(words[1]);
        }

        public int getCommand() {
            return command;
        }

        public int getUnit() {
            return unit;
        }
    }

    /**
     * Constructor.
     */
    public Day2() {
        super ("Dive!", 2021, 2);
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        long horizontal = 0;
        long depth = 0;

        for (int i = 0; i < puzzle.length; i++) {
            switch (puzzle[i].getCommand()) {
                case Command.FORWARD:
                    horizontal = horizontal + puzzle[i].getUnit();
                    break;
                case Command.DOWN:
                    depth = depth + puzzle[i].getUnit();
                    break;
                case Command.UP:
                    depth = depth - puzzle[i].getUnit();
                    break;
            }
        }

        return horizontal * depth;
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        long horizontal = 0;
        long depth = 0;
        long aim = 0;

        for (int i = 0; i < puzzle.length; i++) {
            switch (puzzle[i].getCommand()) {
                case Command.FORWARD:
                    horizontal = horizontal + puzzle[i].getUnit();
                    depth = depth + (aim * puzzle[i].getUnit());
                    break;
                case Command.DOWN:
                    aim = aim + puzzle[i].getUnit();
                    break;
                case Command.UP:
                    aim = aim - puzzle[i].getUnit();
                    break;
            }
        }

        return horizontal * depth;
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        ArrayList<Command> list = input.map(x -> new Command(x))
                                       .collect(Collectors.toCollection(ArrayList<Command>::new)); 
        puzzle = list.toArray(new Command[list.size()]); 
    }
}