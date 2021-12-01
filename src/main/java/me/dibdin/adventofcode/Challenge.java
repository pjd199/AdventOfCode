package me.dibdin.adventofcode;

import java.util.stream.Stream;

public interface Challenge {

    public String getName();

    public int getYear();

    public int getDay();

    public void solve(Stream<String> input);

    public String getPartOneResult();

    public String getPartTwoResult();
}