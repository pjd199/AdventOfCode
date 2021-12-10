package me.dibdin.adventofcode.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 8: Seven Segment Search.
 * https://adventofcode.com/2021/day/8
 */
public class Day8 extends AbstractChallenge {

    ArrayList<String[]> signalsPatterns = null;
    ArrayList<String[]> outputValues = null;

    /**
     * Constructor.
     */
    public Day8() {
        super("Seven Segment Search", 2021, 8);
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if ((signalsPatterns == null) || (outputValues == null)) {
            throw new IllegalStateException("No puzzle input set");
        }

        long count = 0;

        for (int i = 0; i < outputValues.size(); i++) {
            for (String value : outputValues.get(i)) {
                if ((value.length() == 2) || // a 1 digit
                        (value.length() == 4) || // a 4 digit
                        (value.length() == 3) || // a 7 digit
                        (value.length() == 7)) {// an 8 digit
                    count++;
                }
            }

        }

        return count;
    }

    /**
     * Returns true if each letter of the chat array can be found in str
     * 
     * @param str     the string to search
     * @param letters the characters to look for
     * @return true if every character in the array appears in the string, otherwise
     *         false.
     */
    private boolean containsEachLetterOf(String str, char[] letters) {
        boolean result = true;

        for (int i = 0; i < letters.length; i++) {
            result = result && str.contains(String.valueOf(letters[i]));
        }

        return result;
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if ((signalsPatterns == null) || (outputValues == null)) {
            throw new IllegalStateException("No puzzle input set");
        }

        long sum = 0;

        for (int i = 0; i < signalsPatterns.size(); i++) {
            // we will be removing patterns from this list as each one is identified
            ArrayList<String> patterns = new ArrayList<String>(Arrays.asList(signalsPatterns.get(i)));

            // this will be the mapping, from pattern to a digit (represented as a string);
            HashMap<String, Character> map = new HashMap<String, Character>();

            // find digit 1 - pattern length 2
            for (String str : patterns) {
                if (str.length() == 2) {
                    map.put(str, '1');
                    patterns.remove(str);
                    break;
                }
            }

            // find digit 4 - pattern length 4
            String four = null;
            for (String str : patterns) {
                if (str.length() == 4) {
                    map.put(str, '4');
                    patterns.remove(str);
                    four = str;
                    break;
                }
            }

            // find digit 7 - pattern length 3
            String seven = null;
            for (String str : patterns) {
                if (str.length() == 3) {
                    map.put(str, '7');
                    patterns.remove(str);
                    seven = str;
                    break;
                }
            }

            // find digit 8 - pattern length 7
            for (String str : patterns) {
                if (str.length() == 7) {
                    map.put(str, '8');
                    patterns.remove(str);
                    break;
                }
            }

            // find digit 9 - pattern length 6 and contains all of digit 4's pattern
            for (String str : patterns) {
                if (str.length() == 6 && containsEachLetterOf(str, four.toCharArray())) {
                    map.put(str, '9');
                    patterns.remove(str);
                    break;
                }
            }

            // find digit 3 - pattern length 5 and contains all of digit 7's pattern
            for (String str : patterns) {
                if (str.length() == 5 && containsEachLetterOf(str, seven.toCharArray())) {
                    map.put(str, '3');
                    patterns.remove(str);
                    break;
                }
            }

            // find digit 6 - pattern length 6 and contains all of digit 5's pattern
            // find digit 5 - pattern length 5
            String[] twoOrFives = patterns.stream().filter((str) -> (str.length() == 5)).toArray(String[]::new);
            outerLoop: for (String str : patterns) {
                if (str.length() == 6) {
                    for (String twoOrFive : twoOrFives) {
                        if (containsEachLetterOf(str, twoOrFive.toCharArray())) {
                            // found our 5 and our 6
                            map.put(str, '6');
                            patterns.remove(str);
                            map.put(twoOrFive, '5');
                            patterns.remove(twoOrFive);
                            break outerLoop;
                        }
                    }
                }
            }

            // find digit 0 - pattern length 6
            for (String str : patterns) {
                if (str.length() == 6) {
                    map.put(str, '0');
                    patterns.remove(str);
                    break;
                }
            }

            // find digit 2 - pattern length 5
            for (String str : patterns) {
                if (str.length() == 5) {
                    map.put(str, '2');
                    patterns.remove(str);
                    break;
                }
            }

            // calculate the output value stream
            String output = Stream.of(outputValues.get(i))
                    .map((String str) -> String.valueOf(map.get(str)))
                    .collect(Collectors.joining());

            // add the output value to the running sum
            sum += Integer.parseInt(output);
        }

        return sum;
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        Iterator<String> iterator = input.iterator();
        signalsPatterns = new ArrayList<String[]>();
        outputValues = new ArrayList<String[]>();

        // read each line of the input
        while (iterator.hasNext()) {
            // split the line into tokens
            String[] tokens = iterator.next().split(" ");

            // if this is a valid input line
            if (tokens.length == 15) {

                // sort the letters of each token
                for (int i = 0; i < tokens.length; i++) {
                    char[] charArray = tokens[i].toCharArray();
                    Arrays.sort(charArray);
                    tokens[i] = String.valueOf(charArray);
                }

                // add the tokens to the puzzle Lists
                signalsPatterns.add(Arrays.copyOfRange(tokens, 0, 10));
                outputValues.add(Arrays.copyOfRange(tokens, 11, 15));
            }
        }

        System.out.println("SP " + signalsPatterns.size() + " OV " + outputValues.size());
    }
}