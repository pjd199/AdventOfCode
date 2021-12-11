package me.dibdin.adventofcode.util;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputDecoder {
    
    public static int[][] decodeAsTwoDimensionalIntArray(Stream<String> input) {
        
        // load the input
        ArrayList<String> list = input.collect(Collectors.toCollection(ArrayList<String>::new));

        // size the puzzle
        int[][] puzzle = new int[list.size()][list.get(0).length()];

        // break down each string in the list into an array of integers
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                puzzle[i][j] = Integer.parseInt(list.get(i).substring(j, j + 1));
            }
        }

        return puzzle;
    }
}
