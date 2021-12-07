package me.dibdin.adventofcode.year2020;

import me.dibdin.adventofcode.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2020, Day 2
 */
@TestMethodOrder(OrderAnnotation.class)
class Day2Test {
    
    static Challenge challenge;
    
    @BeforeAll
    static void initAll() {
        challenge = new Day2();
    }

    @Test
    @Order(1)
    void informationTest() 
    {
        assertTrue(challenge.getName().equals("Password Philosophy"));
        assertTrue(challenge.getYear() == 2020);
        assertTrue(challenge.getDay() == 2);
    }

    @Test
    @Order(2)
    void cannotSolveNullInputTest() {
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
    }

    @Test
    @Order(3)
    void solveWithSampleInputTest() 
    {
        // form the test  input stream
        Stream<String> input = Stream.of(
            "1-3 a: abcde", 
            "1-3 b: cdefg", 
            "2-9 c: ccccccccc"
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(2, challenge.solvePartOne());
        assertEquals(1, challenge.solvePartTwo());
    }

    /**
     * Test the solution with actual input data from the challenge.
     */
    @Test
    @DisplayName("Solve With Actual Input Test")
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
            assertEquals(483, challenge.solvePartOne());
            assertEquals(482, challenge.solvePartTwo());
        }
    }
}
