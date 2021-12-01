package me.dibdin.adventofcode.year2020;

import me.dibdin.adventofcode.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2020, Day 1
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
        assertTrue(challenge.getName().equals("Report Repair"));
        assertTrue(challenge.getYear() == 2020);
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
            "1721",
            "979",
            "366",
            "299",
            "675",
            "1456"
        );

        // find the solution
        challenge.setPuzzleInput(input);

        // assert the result is corrent
        assertEquals(514579, challenge.solvePartOne());
        assertEquals(241861950, challenge.solvePartTwo());
    }
}
