package me.dibdin.adventofcode.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import me.dibdin.adventofcode.Challenge;

/**
 * Unit tests for Year 2021, Day 7
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Day 7 Test")
class Day7Test {
    
    static Challenge challenge;
    
    @BeforeAll
    static void initAll() {
        challenge = new Day7();
    }

    @Test
    @DisplayName("Information Test")
    @Order(1)
    void informationTest() 
    {
        assertTrue(challenge.getName().equals("The Treachery of Whales"));
        assertTrue(challenge.getYear() == 2021);
        assertTrue(challenge.getDay() == 7);
    }

    @Test
    @DisplayName("No Puzzle Input Test")
    @Order(2)
    void cannotSolveNullInputTest() {
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
    }

    @Test
    @Order(3)
    @DisplayName("Solving With Sample Input Test")
    void solveWithSampleInputTest()
    {
        // form the test  input stream
        Stream<String> input = Stream.of(
            "16,1,2,0,4,2,7,1,2,14"
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(37, challenge.solvePartOne());
        assertEquals(168, challenge.solvePartTwo());
    }

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
            assertEquals(349769, challenge.solvePartOne());
            assertEquals(99540554, challenge.solvePartTwo());
        }
    }
}
