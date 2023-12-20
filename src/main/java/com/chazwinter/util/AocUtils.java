package com.chazwinter.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/* This class will contain some utilities for helping to solve Advent of Code problems.
* Maybe I can be one of those fancy people who can solve a problem in under 6 hours haha. */
public class AocUtils {
    /**
     * Helper method to extract any Collection of numbers from a String. Must specify the Collection type.
     * I used a Stream! Alyson would be proud.
     * If you are sure that there is only one number, you can use extractNumberFromString() instead.
     * @param s The string containing the numbers we need.
     * @return A Collection containing those numbers.
     */
    public static <T extends Collection<Integer>> T extractNumbersFromString(
            String s, Collector<Integer, ?, T> collector) {
        String[] splitNumbers = s.split("\\s+");
        return Arrays.stream(splitNumbers)
                .filter(str -> !str.isEmpty() && isNumeric(str))
                .map(Integer::parseInt)
                .collect(collector);
    }

    /**
     * Helper method for pulling a single number out of a String.
     * If there may be multiple numbers, use extractNumbersFromString(), which returns a Collection.
     * @param str The string to check (ex: "Card 123").
     * @return The number that was found (ex: 123).
     */
    public static int extractNumberFromString(String str) {
        /* Compile a RegEx pattern for extracting numbers. */
        Matcher matcher = Pattern.compile("\\d+").matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        throw new IllegalArgumentException("No number in that String.");
    }

    /**
     * Helper method to determine if a String consists of numbers.
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
