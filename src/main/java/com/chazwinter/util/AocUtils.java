package com.chazwinter.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;

/* This class will contain some utilities for helping to solve Advent of Code problems.
* Maybe I can be one of those fancy people who can solve a problem in under 6 hours haha. */
public class AocUtils {
    /**
     * The MVP utility method! Accepts AoC input files line by line for further processing.
     * @param filePath String path to the input file for the current AoC day.
     * @param processLine Consumer to accept the line and perform actions on it for each AoC day.
     */
    public static void processInputFile(String filePath, Consumer<String> processLine) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                processLine.accept(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("File could not be read. " + e);
        }
    }

    /**
     * Helper method to extract any Collection of numbers from a String. Must specify the Collection type.
     * I used a Stream! Alyson would be proud.
     * If you are sure that there is only one number, you can use extractIntFromString() instead.
     * @param s The string containing the numbers we need.
     * @return A Collection containing those numbers.
     */
    public static <T extends Collection<Integer>> T extractIntegersFromString(
            String s, Collector<Integer, ?, T> collector) {
        String[] splitNumbers = s.split("\\s+");
        return Arrays.stream(splitNumbers)
                .filter(str -> !str.isEmpty() && isNumeric(str))
                .map(Integer::parseInt)
                .collect(collector);
    }

    /**
     * Same as above, but gets Longs instead of Integers.
     */
    public static <T extends Collection<Long>> T extractLongsFromString(
            String s, Collector<Long, ?, T> collector) {
        String[] splitNumbers = s.split("\\s+");
        return Arrays.stream(splitNumbers)
                .filter(str -> !str.isEmpty() && isNumeric(str))
                .map(Long::parseLong)
                .collect(collector);
    }

    /**
     * Helper method for pulling a single Integer out of a String.
     * If there may be multiple numbers, use extractIntegersFromString(), which returns a Collection.
     * If the number is too large for an Integer, use extractLongFromString().
     * @param str The string to check (ex: "Card 123").
     * @return The number that was found (ex: 123).
     */
    public static int extractIntFromString(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        throw new IllegalArgumentException("No number in that String.");
    }

    /**
     * Same as above, except it gets a Long instead of an Integer.
     */
    public static long extractLongFromString(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return Long.parseLong(matcher.group());
        }
        throw new IllegalArgumentException("No number in that String.");
    }

    /**
     * Helper method to determine if a String consists of a number.
     * @param s The string that might be a number.
     * @return true if the String is a number.
     */
    public static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Helper method to determine if a cell in a 2D array is in bounds.
     * @param row The row of that cell.
     * @param col The column of that cell.
     * @param arr The array to use as reference.
     * @return True if the row and column are in bounds for that array.
     */
    public static boolean isInBounds(int row, int col, int[][] arr) {
        return row >= 0 && col >= 0 && row < arr.length && col < arr[row].length;
    }
}
