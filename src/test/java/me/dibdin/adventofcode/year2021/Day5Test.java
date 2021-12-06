package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.Challenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.IllegalStateException;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2021, Day 5
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Day 5 Test")
class Day5Test {

    static Challenge challenge;

    /**
     * Construct the challenge object
     */
    @BeforeAll
    static void initAll() {
        challenge = new Day5();
    }

    /**
     * Check the challenge information is correct
     */
    @Test
    @DisplayName("Information Test")
    @Order(1)
    void informationTest() {
        assertTrue(challenge.getName().equals("Hydrothermal Venture"));
        assertTrue(challenge.getYear() == 2021);
        assertTrue(challenge.getDay() == 5);
    }

    /**
     * Test that the challenge cannot be solved before input is provided
     */
    @Test
    @DisplayName("No Puzzle Input Test")
    @Order(2)
    void cannotSolveNullInputTest() {
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
    }

    /**
     * Test the solution with the sample input data from the challenge description.
     */
    @Test
    @Order(3)
    @DisplayName("Solving With Sample Input Test")
    void solveWithSampleInputTest() {
        // form the test input stream
        Stream<String> input = Stream.of(
                "0,9 -> 5,9",
                "8,0 -> 0,8",
                "9,4 -> 3,4",
                "2,2 -> 2,1",
                "7,0 -> 7,4",
                "6,4 -> 2,0",
                "0,9 -> 2,9",
                "3,4 -> 1,4",
                "0,0 -> 8,8",
                "5,5 -> 8,2");

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(5, challenge.solvePartOne());
        assertEquals(12, challenge.solvePartTwo());
    }

    /**
     * Test the solution with actual input data from the challenge.
     */
    @Test
    @DisplayName("Solving With Actual Input Test")
    @Order(4)
    void solveWithActualInputTest() {
        String filename = String.format("data/year%d/day%d.txt", challenge.getYear(), challenge.getDay());
        try (Stream<String> stream = new BufferedReader(
                new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream(filename)))
                                .lines()) {

            // Solve the challenge
            challenge.setPuzzleInput(stream);

            // assert the result is corrent
            assertEquals(8622, challenge.solvePartOne());
            assertEquals(22037, challenge.solvePartTwo());
        }
    }
}
