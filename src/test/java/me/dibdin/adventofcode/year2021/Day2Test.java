package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.Challenge;

import java.util.stream.Stream;
import java.lang.IllegalStateException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2021, Day 1
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
        assertTrue(challenge.getName().equals("Dive!"));
        assertTrue(challenge.getYear() == 2021);
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
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2"
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(150, challenge.solvePartOne());
        assertEquals(900, challenge.solvePartTwo());
    }
}
