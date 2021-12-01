package me.dibdin.adventofcode;

import java.util.stream.Stream;

public abstract class AbstractChallenge implements Challenge {

    private String name;
    private int year;
    private int day;

    protected AbstractChallenge(String name, int year, int day) {
        this.name = name;
        this.year = year;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public abstract void setPuzzleInput(Stream<String> input);

    public abstract long solvePartOne();

    public abstract long solvePartTwo();
}
