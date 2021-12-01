package me.dibdin.adventofcode.year2020;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

/**
 * Advent of Code Challenge 2020 - Day 6: Custom Customs.
 * https://adventofcode.com/2020/day/6
 */
public class Day6 extends AbstractChallenge {

    Group[] puzzle = null;

    public class Group {
        private ArrayList<String> people;
        private int[] questionsAnswered;

        public Group() {
            people = new ArrayList<String>();
            questionsAnswered = new int[26];
            Arrays.fill(questionsAnswered, 0);
        }

        public void addPerson(String line) {
            people.add(line);

            // work out which questions have been answered
            char[] letters = line.toCharArray();
            for (int i = 0; i < letters.length; i++) {
                questionsAnswered[(letters[i] - 'a')]++;
            }
        }

        public int size() {
            return people.size();
        }

        public int getNumberOfQuestionsAnswered() {
            int count = 0;

            for (int i = 0; i < questionsAnswered.length; i++) {
                if (questionsAnswered[i] > 0) {
                    count++;
                }
            }
            return count;
        }

        public int getNumberOfQuestionsAnsweredByAll() {
            int count = 0;

            for (int i = 0; i < questionsAnswered.length; i++) {
                if (questionsAnswered[i] == people.size()) {
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * Initialise the instance, with the name, year and day of the challege.
     */
    public Day6() {
        super ("Custom Customs", 2020, 6);
    }

    public long solvePartOne() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        long count = 0;
        for (int i = 0; i < puzzle.length; i++) {
            count = count + puzzle[i].getNumberOfQuestionsAnswered();
        }
        return count;
    }

    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        long count = 0;
        for (int i = 0; i < puzzle.length; i++) {
            count = count + puzzle[i].getNumberOfQuestionsAnsweredByAll();
        }
        return count;
    }
    
    /**
     * Solve both parts of the puzzle for the given input. 
     * @param input a stream of strings to process as the puzzle input
     */
    public void setPuzzleInput(Stream<String> input) {

        ArrayList<Group> groups = new ArrayList<Group>();
        Iterator<String> lines = input.iterator();
        Group group = new Group();

        while (lines.hasNext()) {
            String line = lines.next();
            if (!line.equals("")) {
                group.addPerson(line);
            } else {
                groups.add(group);
                group = new Group();
            }
        }
        if (group.size() > 0) {
            groups.add(group);
        }

        puzzle = groups.toArray(new Group[groups.size()]);
    }
}