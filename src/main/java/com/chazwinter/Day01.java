package com.chazwinter;

import com.chazwinter.util.AocUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01 {
    Map<String, Integer> wordNumMap = Map.of(
            "one", 1,            "two", 2,            "three", 3,
            "four", 4,           "five", 5,           "six", 6,
            "seven", 7,          "eight", 8,          "nine", 9,
            "zero", 0
    );
    public int sumNumbersFromStrings(String filePath, int part) {
        AtomicInteger sum = new AtomicInteger(0);
        AocUtils.processInputFile(filePath, (line) -> {
            if (part == 1) {
                sum.addAndGet(processDigitsFromInput(line));
            }
            if (part == 2) {
                sum.addAndGet(processTextNums(line));
            }
        });

        return sum.get();
    }

    /**
     * For Part 1. Find the earliest and latest digit in each string, and
     * merge them into a two-digit number.
     * @param line the String to evaluate
     * @return the merged two-digit integer
     */
    private int processDigitsFromInput(String line) {
        int left = 0, right = line.length() - 1;
        int leftDigit = 0, rightDigit = 0;
        while (left < line.length()) {
            char testCharLeft = line.charAt(left);
            if (Character.isDigit(testCharLeft)) {
                leftDigit = 10 * (testCharLeft - '0');
                break;
            }
            left++;
        }
        while (right >= 0) {
            char testCharRight = line.charAt(right);
            if (Character.isDigit(testCharRight)) {
                rightDigit = testCharRight - '0';
                break;
            }
            right--;
        }
        return leftDigit + rightDigit;
    }

    /**
     * This is not efficient. Scan the line for the presence of each number,
     * then process the earliest and latest "digits". For Part 2.
     */
    private int processTextNums(String line) {
        Pattern pattern = Pattern.compile("\\d");
        int left = 0, right = line.length() - 1;
        int leftDigit = 0, rightDigit = 0;
        while (left <= line.length()) {
            String leftString = line.substring(0, left);    // Strings will be a, ab, abc, etc...
            int foundDigit = matchTextOrDigit(leftString, pattern);
            if (foundDigit != -1) {
                leftDigit = 10 * foundDigit;
                break;
            }
            left++;
        }
        while (right >= 0) {
            String rightString = line.substring(right, line.length());  // Strings z, yz, xyz, etc...
            int foundDigit = matchTextOrDigit(rightString, pattern);
            if (foundDigit != -1) {
                rightDigit = foundDigit;
                break;
            }
            right--;
        }
        //System.out.println(line + ": " + leftDigit + " and " + rightDigit);
        return leftDigit + rightDigit;
    }

    /**
     * Helper method to check is a given string contains either a digit,
     * or the spelled out word version of a digit.
     * @param input The string to evaluate.
     * @param pattern The RegEx pattern for finding a digit.
     * @return the integer form of whatever we found, or -1 if no digits found.
     */
    private int matchTextOrDigit(String input, Pattern pattern) {
        // Look for a digit
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {   // Digit present in substring.
            String foundDigit = matcher.group();
            //System.out.println("FOUND DIGIT: " + foundDigit);
            return Integer.parseInt(foundDigit);
        }
        // Now look for the spelled out word
        for (String digit : wordNumMap.keySet()) {
            if (input.contains(digit)) {
                //System.out.println("FOUND WORD: " + digit);
                return wordNumMap.get(digit);
            }
        }
        return -1;  // No number found in substring.
    }
}
