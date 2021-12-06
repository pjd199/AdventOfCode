package me.dibdin.adventofcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Class;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Advent of Code app - main entry point
 */
public class App {
    public static void main(String[] args)
    {
        System.out.println( "------------------");
        System.out.println( "| Advent of Code |");
        System.out.println( "------------------");

        int year;
        int day;
        Challenge challenge;

        // Ask the user which challenge to run
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Which challege would you like to run?");
            System.out.print("Year: ");
            year = scanner.nextInt();

            System.out.print("Day: ");
            day = scanner.nextInt();

            // find the Class to solve the requested challenge
            challenge = (Challenge) Class.forName("me.dibdin.adventofcode.year" + year + ".Day" + day)
                                            .getConstructor()
                                            .newInstance();

            // tell the user we're read to go
            System.out.println("");
            System.out.println("Solving the '" + challenge.getName() + "' Challenge (Year " 
                                    + challenge.getYear() + ", Day " + challenge.getDay() + ")");
                                    
            // Open the input file as an AutoCloseable resource, and make it available as a stream of lines
            String filename = String.format("data/year%d/day%d.txt", challenge.getYear(), challenge.getDay());
            try (Stream<String> stream = new BufferedReader(
                                            new InputStreamReader(
                                                ClassLoader.getSystemResourceAsStream(filename)))
                                        .lines()) {

                // Solve the challenge
                challenge.setPuzzleInput(stream);

                // Print the results
                System.out.println("Part One Result: " + challenge.solvePartOne());
                System.out.println("Part Two Result: " + challenge.solvePartTwo());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find Class for the specified year and day");
        } catch (InputMismatchException e) {
            System.out.println("Please run again with valid input numbers");
        } catch (Exception e) {
            System.out.println("Oops!! Something went wrong.");
            e.printStackTrace();
        }
    }
}
