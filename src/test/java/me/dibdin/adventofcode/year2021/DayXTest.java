package me.dibdin.adventofcode.year2021;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import me.dibdin.adventofcode.Challenge;
import me.dibdin.adventofcode.util.ChallengeFactory;

/**
 * Unit tests for Year 2021 Challenges
 */
@DisplayName("Year 2021 Day X Test")
class DayXTest {

    /**
     * The year under test
     */
    private static final int year = 2021;

    /**
     * An enumeration of puzzles used as input for the parameterize testing.
     */
    static enum Puzzles {
        // Results: { {EXAMPLE PART_ONE, PART_TWO}, {PRODUCTION PART_ONE, PART_TWO} }
        DAY1(1, "Sonar Sweep", new long[][] { { 7, 5 }, { 1665, 1702 } }),
        DAY2(2, "Dive!", new long[][] { { 150, 900 }, { 1604850, 1685186100 } }),
        DAY3(3, "Binary Diagnostic", new long[][] { { 198, 230 }, { 3969000, 4267809 } }),
        DAY4(4, "Giant Squid", new long[][] { { 4512, 1924 }, { 27027, 36975 } }),
        DAY5(5, "Hydrothermal Venture", new long[][] { { 5, 12 }, { 8622, 22037 } }),
        DAY6(6, "Lanternfish", new long[][] { { 5934, 26984457539L }, { 385391, 1728611055389L } }),
        DAY7(7, "The Treachery of Whales", new long[][] { { 37, 168 }, { 349769, 99540554 } });
        // DAYX(X, "X", new long[][] { { 0, 0 }, { 0, 0 } });

        private int day;
        private String name;
        private long[][] results;

        /**
         * Constructor.
         * 
         * @param day     the day of the puzzle
         * @param name    the name of the puzzle
         * @param results the results formatted as { {EXAMPLE PART_ONE, PART_TWO},
         *                {PRODUCTION PART_ONE, PART_TWO} }
         */
        private Puzzles(int day, String name, long[][] results) {
            this.day = day;
            this.name = name;
            this.results = results;
        }

        /**
         * The day of the puzzle.
         * 
         * @return the day
         */
        private int getDay() {
            return day;
        }

        /**
         * The name of the puzzle.
         * 
         * @return the name
         */
        private String getName() {
            return name;
        }

        /**
         * The expected results of the puzzle.
         * 
         * @return the results formatted as { {EXAMPLE PART_ONE, PART_TWO},
         *         {PRODUCTION PART_ONE, PART_TWO} }
         */
        private long[][] getResults() {
            return results;
        }
    }

    static final int EXAMPLE = 0;
    static final int PRODUCTION = 1;

    static final int PART_ONE = 0;
    static final int PART_TWO = 1;

    String[] pathTemplate = {
            "./example-data/year%d/day%d.txt", // EXAMPLE
            "./data/year%d/day%d.txt" // PRODUCTION
    };

    /**
     * Check the year, day and name are correct.
     * 
     * @param puzzle The puzzle under test.
     */
    @ParameterizedTest
    @DisplayName("Information Test")
    @EnumSource(Puzzles.class)
    void informationTest(Puzzles puzzle) {

        // Initialise the challenge
        Challenge challenge = assertDoesNotThrow(() -> {
            return ChallengeFactory.getChallengeInstance(year, puzzle.getDay());
        });

        // Check all the information is correct
        assertTrue(challenge.getName().equals(puzzle.getName()));
        assertTrue(challenge.getYear() == year);
        assertTrue(challenge.getDay() == puzzle.getDay());
    }

    /**
     * Check that an exception is throw when trying to solve the challenge before
     * setting input.
     * 
     * @param puzzle The puzzle under test.
     */
    @ParameterizedTest
    @DisplayName("Cannot Solve Before Setting Puzzle Input Test")
    @EnumSource(Puzzles.class)
    void cannotSolveBeforeSettingPuzzleInputTest(Puzzles puzzle) {

        // Initialise the challenge
        Challenge challenge = assertDoesNotThrow(() -> {
            return ChallengeFactory.getChallengeInstance(year, puzzle.getDay());
        });

        // Check it throws an exception when there is no input
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
        assertThrows(IllegalStateException.class, () -> challenge.solvePartTwo());
    }

    /**
     * Solve part one of the puzzle, first with the sample input, then the
     * production input
     * 
     * @param puzzle The puzzle under test.
     */
    @ParameterizedTest
    @EnumSource(Puzzles.class)
    @DisplayName("Solve Part One")
    void solvePartOneTest(Puzzles puzzle) {

        // Initialise the challenge
        Challenge challenge = assertDoesNotThrow(() -> {
            return ChallengeFactory.getChallengeInstance(year, puzzle.getDay());
        });

        // Open the example input file as a stream
        final String exampleFilename = String.format(pathTemplate[EXAMPLE], year, puzzle.getDay());
        final Stream<String> exampleStream = assertDoesNotThrow(() -> {
            return new BufferedReader(
                    new InputStreamReader(
                            ClassLoader.getSystemResourceAsStream(exampleFilename)))
                                    .lines();
        });

        // Set the puzzle input
        assertDoesNotThrow(() -> {
            challenge.setPuzzleInput(exampleStream);
        });

        // Check the result is correct
        assertEquals(puzzle.getResults()[EXAMPLE][PART_ONE], challenge.solvePartOne());

        // Open the production input file as a stream
        final String productionFilename = String.format(pathTemplate[PRODUCTION], year, puzzle.getDay());
        Stream<String> productionStream = assertDoesNotThrow(() -> {
            return new BufferedReader(
                    new InputStreamReader(
                            ClassLoader.getSystemResourceAsStream(productionFilename)))
                                    .lines();
        });

        // Set the puzzle input
        assertDoesNotThrow(() -> {
            challenge.setPuzzleInput(productionStream);
        });

        // Check the result is correct
        assertEquals(puzzle.getResults()[PRODUCTION][PART_ONE], challenge.solvePartOne());
    }

    /**
     * Solve part two of the puzzle, first with the sample input, then the
     * production input
     * 
     * @param puzzle The puzzle under test.
     */
    @ParameterizedTest
    @EnumSource(Puzzles.class)
    @DisplayName("Solve Part Two")
    void solvePartTwoTest(Puzzles puzzle) {

        // Initialise the challenge
        Challenge challenge = assertDoesNotThrow(() -> {
            return ChallengeFactory.getChallengeInstance(year, puzzle.getDay());
        });

        // Open the example input file as a stream
        final String exampleFilename = String.format(pathTemplate[EXAMPLE], year, puzzle.getDay());
        final Stream<String> exampleStream = assertDoesNotThrow(() -> {
            return new BufferedReader(
                    new InputStreamReader(
                            ClassLoader.getSystemResourceAsStream(exampleFilename)))
                                    .lines();
        });

        // Set the puzzle input
        assertDoesNotThrow(() -> {
            challenge.setPuzzleInput(exampleStream);
        });

        // Check the result is correct
        assertEquals(puzzle.getResults()[EXAMPLE][PART_TWO], challenge.solvePartTwo());

        // Open the production input file as a stream
        final String productionFilename = String.format(pathTemplate[PRODUCTION], year, puzzle.getDay());
        Stream<String> productionStream = assertDoesNotThrow(() -> {
            return new BufferedReader(
                    new InputStreamReader(
                            ClassLoader.getSystemResourceAsStream(productionFilename)))
                                    .lines();
        });

        // Set the puzzle input
        assertDoesNotThrow(() -> {
            challenge.setPuzzleInput(productionStream);
        });

        // Check the result is correct
        assertEquals(puzzle.getResults()[PRODUCTION][PART_TWO], challenge.solvePartTwo());
    }
}