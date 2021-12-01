package me.dibdin.adventofcode.year2020;

import me.dibdin.adventofcode.AbstractChallenge;

import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Advent of Code Challenge 2020 - Day 4: Passport Processing.
 * https://adventofcode.com/2020/day/4
 */
public class Day4 extends AbstractChallenge {

    Passport[] puzzle = null;

    public class Passport {
        
        /**
         * The HashMap to store all the passport fields
         */
        private HashMap<String, String> fields;

        /**
         * Initialise a new Passport object
         * @param inputFields input data for the passport
         */
        public Passport(Map<String, String> inputFields) {
            fields = new HashMap<String, String>();
            Set<Map.Entry<String, String>> entries = inputFields.entrySet();
            entries.forEach(entry -> fields.put(entry.getKey(), entry.getValue()));
        }

        /**
         * Add or replace the key-value pair of the passport
         * @param key the key to store value under
         * @param value the value to store under key
         */
        public void setValue(String key, String value) {
            fields.put(key, value);
        }

        /**
         * Returns the value of the password key
         * @param key key of the value to return
         * @return the value stored under key
         */
        public String getValue(String key) {
            return (String) fields.get(key);
        }

        /* 
        * Check this Passport has all the right fields
        */
        public boolean hasRequiredFields(boolean requireCountryId) {
            return fields.containsKey("byr") &&     // Birth Year
                    fields.containsKey("iyr") &&    // Issue Year
                    fields.containsKey("eyr") &&    // Expiration Year
                    fields.containsKey("hgt") &&    // Height
                    fields.containsKey("hcl") &&    // Hair Color
                    fields.containsKey("ecl") &&    // Eye Color
                    fields.containsKey("pid") &&    // Passport ID
                    (!requireCountryId || fields.containsKey("cid"));   // Country ID
        }

        /*
         * Is all the data valid?
         */
        public boolean isDataValid() {
            return hasRequiredFields(false) &&
                    isYearValid(fields.get("byr"), 1920, 2020) &&
                    isYearValid(fields.get("iyr"), 2010, 2020) &&
                    isYearValid(fields.get("eyr"), 2020, 2030) &&
                    (isDistanceValid(fields.get("hgt"), "cm", 150, 193) || isDistanceValid(fields.get("hgt"), "in", 59, 76)) &&
                    isHexColorCodeValid(fields.get("hcl")) &&
                    isColorCodeValid(fields.get("ecl")) &&
                    isIdValid(fields.get("pid"));
                    // Country ID is always ignored
        }

        /*
         * Validate a year - string of four digits, with the date between lower and upper
         */
        private boolean isYearValid(String value, int lower, int upper) {
            // is the field look like a year in string form?
            if ((value != null) && value.matches("[0-9]{4}")) {
                // is the year between upper and lower?
                int year = Integer.parseInt(value); 
                return (lower <= year) && (year <= upper);
            } else {
                return false;
            }
        }

        /*
        * Validate a distance - a number followed by a unit, with the number between lower and upper
        */
        private boolean isDistanceValid(String value, String unit, int lower, int upper) {
            // check looks like the right format
            if ((value != null) && value.matches("[0-9]+" + unit)) {
                //get the digits
                int distance = Integer.parseInt(value.substring(0, value.length() - unit.length()));
                return (lower <= distance) && (distance <= upper);
            } else {
                return false;
            }
        }

        /*
         * Validate color: - a # followed by exactly six characters 0-9 or a-f.
         */
        private boolean isHexColorCodeValid(String value) {
            // check looks like the right format
            return (value != null) && value.matches("#([0-9]|[a-f]){6}");
        }

        /*
         * Validate color code
         */
        private boolean isColorCodeValid(String value) {
            // is the colour code is valid?
            return (value != null) && value.matches("(amb)|(blu)|(brn)|(gry)|(grn)|(hzl)|(oth)");
        }

        /*
         * Validate an ID year - a nine-digit number, including leading zeroes.
         */
        private boolean isIdValid(String value) {
            // does the field look like an ID field?
            return (value != null) && value.matches("[0-9]{9}");
        }
    }

    /**
     * Initialise the instance, with the name, year and day of the challege.
     */
    public Day4() {
        super ("Passport Processing", 2020, 4);
    }

    public long solvePartOne() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        long validPassportCount = 0;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i].hasRequiredFields(false)) {
                validPassportCount++;
            }
        }
        return validPassportCount;
    }

    public long solvePartTwo() {
        if (puzzle == null) { 
            throw new IllegalStateException("No puzzle input set");
        }

        long validPassportCount = 0;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i].isDataValid()) {
                validPassportCount++;
            }
        }
        return validPassportCount;
    }
    
    /**
     * Solve both parts ofhar hte puzzle for the given input. 
     * @param input a stream of strings to process as the puzzle input
     */
    public void setPuzzleInput(Stream<String> input) {
        ArrayList<Passport> passportList = new ArrayList<Passport>();
        HashMap<String, String> fields = new HashMap<String, String>();

        // Read passports from the input
        Iterator<String> lines = input.iterator();
        while (lines.hasNext()) {
            String line = lines.next();
            if (!line.equals("")) {
                // line has fields to parse
                String pairs[] = line.split(" ");
                for (int i = 0; i < pairs.length; i++) {
                    String pair[] = pairs[i].split(":");
                    fields.put(pair[0], pair[1]);    
                }
            } else {
                // blank line, so create the passport
                passportList.add(new Passport(fields));
                fields.clear();
            }
        }
        // add the last passport, if fields have been found
        if (!fields.isEmpty()) {
            passportList.add(new Passport(fields));
            fields.clear();
        }

        // Solve the puzzles
        Passport[] passportArray = new Passport[passportList.size()];
        puzzle = passportList.toArray(passportArray);
    }
}