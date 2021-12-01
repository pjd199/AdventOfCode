package me.dibdin.adventofcode;

import java.util.stream.Stream;

public interface Challenge {

    public String getName();

    public int getYear();

    public int getDay();

    public void setPuzzleInput(Stream<String> input);

    public long solvePartOne();

    public long solvePartTwo();
}