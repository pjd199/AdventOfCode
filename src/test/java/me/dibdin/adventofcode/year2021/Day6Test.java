package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.Challenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import java.lang.IllegalStateException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2021, Day 6
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Day 6 Test")
class Day6Test {
    
    static Challenge challenge;
    
    @BeforeAll
    static void initAll() {
        challenge = new Day6();
    }

    @Test
    @DisplayName("Information Test")
    @Order(1)
    void informationTest() 
    {
        assertTrue(challenge.getName().equals("Lanternfish"));
        assertTrue(challenge.getYear() == 2021);
        assertTrue(challenge.getDay() == 6);
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
            "3,4,3,1,2"
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(5934, challenge.solvePartOne());
        assertEquals(26984457539L, challenge.solvePartTwo());
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
            assertEquals(385391, challenge.solvePartOne());
            assertEquals(1728611055389L, challenge.solvePartTwo());
        }
    }
}
