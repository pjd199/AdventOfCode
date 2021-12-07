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
 * Unit tests for Year 2021, Day 1
 */
@TestMethodOrder(OrderAnnotation.class)
class Day1Test {
    
    static Challenge challenge;
    
    @BeforeAll
    static void initAll() {
        challenge = new Day1();
    }

    @Test
    @Order(1)
    void informationTest() 
    {
        assertTrue(challenge.getName().equals("Sonar Sweep"));
        assertTrue(challenge.getYear() == 2021);
        assertTrue(challenge.getDay() == 1);
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
            "199",
            "200",
            "208",
            "210",
            "200",
            "207",
            "240",
            "269",
            "260",
            "263"
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(7, challenge.solvePartOne());
        assertEquals(5, challenge.solvePartTwo());
    }

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
            assertEquals(1665, challenge.solvePartOne());
            assertEquals(1702, challenge.solvePartTwo());
        }
    }
}
