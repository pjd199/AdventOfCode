package me.dibdin.adventofcode.year2020;
import me.dibdin.adventofcode.*;

import java.util.stream.Stream;
import java.lang.NullPointerException;

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
    void nullBeforeSolvingTest() 
    {
        // assert the pre-test state is null
        assertTrue(challenge.getPartOneResult() == null);
        assertTrue(challenge.getPartTwoResult() == null);
    }

    @Test
    @Order(3)
    void cannotSolveNullInputTest() {
        assertThrows(NullPointerException.class, () -> challenge.solve(null));
    }

    @Test
    @Order(4)
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
        challenge.solve(input);

        // assert the result is corrent
        assertEquals("822", challenge.getPartOneResult());
        assertEquals("821", challenge.getPartTwoResult());
    }
}
