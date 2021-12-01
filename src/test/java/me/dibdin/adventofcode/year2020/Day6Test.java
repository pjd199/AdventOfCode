package me.dibdin.adventofcode.year2020;
import me.dibdin.adventofcode.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.*;

/**
 * Unit tests for Year 2020, Day 6
 */
@TestMethodOrder(OrderAnnotation.class)
class Day6Test {
    
    static Challenge challenge;
    
    @BeforeAll
    static void initAll() {
        challenge = new Day6();
    }

    @Test
    @Order(1)
    void informationTest() 
    {
        assertTrue(challenge.getName().equals("Custom Customs"));
        assertTrue(challenge.getYear() == 2020);
        assertTrue(challenge.getDay() == 6);
    }

    @Test
    @Order(2)
    void cannotSolveNoPuzzleInputTest() {
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
        assertThrows(IllegalStateException.class, () -> challenge.solvePartOne());
    }

    @Test
    @Order(3)
    void solveWithSampleInputTest()
    {
        // form the test  input stream
        Stream<String> input = Stream.of(
            "abc",
            "",
            "a",
            "b",
            "c",
            "",
            "ab",
            "ac",
            "",
            "a",
            "a",
            "a",
            "a",
            "",
            "b"
        );

        // set the input
        challenge.setPuzzleInput(input);

        // assert the result are corrent
        assertEquals(11, challenge.solvePartOne());
        assertEquals(6, challenge.solvePartTwo());
    }
}
