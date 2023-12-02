package com.chazwinter;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        int day = 2;                // Which day do you want to run?
        boolean fullInput = true;  // true = full puzzle input; false = test input only
        String filePath = buildFilePathForToday(day, fullInput);

        /* Code for running each day. Specify day and input type above. */
        if (day == 2) {
            Day02 day02 = new Day02();
            int part1Sum = day02.validGameTotal(filePath, 1);
            System.out.println(part1Sum);
            int part2Sum = day02.validGameTotal(filePath, 2);
            System.out.println(part2Sum);
        }
        if (day == 1) {
            Day01 day01 = new Day01();
            int part1Sum = day01.sumNumbersFromStrings(filePath, 1);
            System.out.println(part1Sum);
            int part2Sum = day01.sumNumbersFromStrings(filePath, 2);
            System.out.println(part2Sum);
        }
    }

    private static String buildFilePathForToday(int day, boolean fullInput) {
        String result = "src/main/resources/";
        result += fullInput ? "input" : "test";
        result += "_day" + String.format("%02d", day);
        System.out.println(result);
        return result;
    }
}