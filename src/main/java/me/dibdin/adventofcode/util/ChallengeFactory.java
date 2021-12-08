package me.dibdin.adventofcode.util;

import java.lang.reflect.InvocationTargetException;

import me.dibdin.adventofcode.Challenge;

/**
 * Factory class for Challenges
 */
public class ChallengeFactory {

    /**
     * Returns a new instance of the challenge for the specied year and day
     * 
     * @param year the year of the challenge
     * @param day  the day of the challenge
     * @return a new instance of the challenge
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Challenge getChallengeInstance(int year, int day) throws ClassNotFoundException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        // find the class, then call it's constructor
        String name = String.format("me.dibdin.adventofcode.year%d.Day%d", year, day);
        return (Challenge) Class.forName(name).getConstructor().newInstance();
    }
}