package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.Challenge;

import java.util.stream.Stream;
import java.lang.IllegalStateException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2021, Day 5
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
        assertTrue(challenge.getName().equals("Hydrothermal Venture"));
        assertTrue(challenge.getYear() == 2021);
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
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"            
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(5, challenge.solvePartOne());
        assertEquals(12, challenge.solvePartTwo());
    }
}
