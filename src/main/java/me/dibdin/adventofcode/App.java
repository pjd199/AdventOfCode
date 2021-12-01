package me.dibdin.adventofcode;

import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Advent of Code app - main entry point
 */
public class App {
    public static void main(String[] args)
    {
        System.out.println( "------------------");
        System.out.println( "| Advent of Code |");
        System.out.println( "------------------");

        // Initialise the challenge
        Challenge challenge = new me.dibdin.adventofcode.year2020.Day6();

        // Open the input file as an AutoCloseable resource, and make it available as a stream of lines
        String filename = String.format("data/year%d/day%d.txt", challenge.getYear(), challenge.getDay());
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(filename))).lines()) {

            // Solve the challenge
            challenge.setPuzzleInput(stream);

            // Print the results
            System.out.println("Solved the '" + challenge.getName() + "' Challenge (Year " + challenge.getYear() + ", Day " + challenge.getDay() + ")");
            System.out.println("Part One Result: " + challenge.solvePartOne());
            System.out.println("Part Two Result: " + challenge.solvePartTwo());
                
        } catch (Exception err) {
            // Something serious has gone wrong
            err.printStackTrace();
        }
    }
}
