package me.dibdin.adventofcode.year2021;

import me.dibdin.adventofcode.Challenge;

import java.util.stream.Stream;
import java.lang.NullPointerException;

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
        challenge.solve(input);

        // assert the result is corrent
        assertEquals("7", challenge.getPartOneResult());
        assertEquals("5", challenge.getPartTwoResult());
    }
}
