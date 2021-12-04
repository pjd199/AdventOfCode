package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.Challenge;

import java.util.stream.Stream;
import java.lang.IllegalStateException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2021, Day 3
 */
@TestMethodOrder(OrderAnnotation.class)
class Day3Test {
    
    static Challenge challenge;
    
    @BeforeAll
    static void initAll() {
        challenge = new Day3();
    }

    @Test
    @Order(1)
    void informationTest() 
    {
        assertTrue(challenge.getName().equals("Binary Diagnostic"));
        assertTrue(challenge.getYear() == 2021);
        assertTrue(challenge.getDay() == 3);
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
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010"
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent 
        assertEquals(198, challenge.solvePartOne());
        assertEquals(230, challenge.solvePartTwo());
    }
}
