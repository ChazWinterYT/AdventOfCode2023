package com.chazwinter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01 {
    Map<String, Integer> wordNumMap = Map.of(
            "one", 1,            "two", 2,            "three", 3,
            "four", 4,           "five", 5,           "six", 6,
            "seven", 7,          "eight", 8,          "nine", 9,
            "zero", 0
    );
    public int sumNumbersFromStrings(String filePath, int part) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int sum = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            if (part == 1) {
                sum += processDigitsFromInput(line);
            }
            if (part == 2) {
                sum += processTextNums(line);
            }
        }
        return sum;
    }

    // part one
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
            String leftString = line.substring(0, left);
            int foundDigit = matchTextOrDigit(leftString, pattern);
            if (foundDigit != -1) {
                leftDigit = 10 * foundDigit;
                break;
            }
            left++;
        }
        while (right >= 0) {
            String rightString = line.substring(right, line.length());
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
