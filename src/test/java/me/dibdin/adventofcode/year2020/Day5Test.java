package me.dibdin.adventofcode.year2020;
import me.dibdin.adventofcode.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2020, Day 5
 */
@TestMethodOrder(OrderAnnotation.class)
class Day5Test {
    
    static Challenge challenge;
    
    @BeforeAll
    static void initAll() {
        challenge = new Day5();
    }

    @Test
    @Order(1)
    void informationTest() 
    {
        assertTrue(challenge.getName().equals("Binary Boarding"));
        assertTrue(challenge.getYear() == 2020);
        assertTrue(challenge.getDay() == 5);
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
            "BFFFBBFRRR",
            "FFFBBBFRRR",
            "BBFFBBFRLL",
            "BBFFBBFRRL",
            "FBFBBFFRLR"
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(822, challenge.solvePartOne());
        assertEquals(821, challenge.solvePartTwo());
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
            assertEquals(963, challenge.solvePartOne());
            assertEquals(592, challenge.solvePartTwo());
        }
    }
}
