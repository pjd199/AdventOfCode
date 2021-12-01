package me.dibdin.adventofcode;

import java.util.stream.Stream;

public abstract class AbstractChallenge implements Challenge {

    private String name;
    private int year;
    private int day;

    private String partOneResult = null;
    private String partTwoResult = null;

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

    protected void setPartOneResult(String result) {
        partOneResult = result;
    }

    protected void setPartTwoResult(String result) {
        partTwoResult = result;
    }

    public abstract void solve(Stream<String> input);

    public String getPartOneResult() {
        return partOneResult;
    }

    public String getPartTwoResult() {
        return partTwoResult;
    }
}
