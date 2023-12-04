package com.chazwinter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int day = 4;                // Which day do you want to run?
        boolean fullInput = true;  // true = full puzzle input; false = test input only
        String filePath = buildFilePathForToday(day, fullInput);

        /* Code for running each day. Specify day and input type above. */
        if (day == 4) {
            Day04 day04 = new Day04();
            int part1Sum = day04.scratchCards(filePath, 1);
            System.out.println(part1Sum);
            int part2Sum = day04.scratchCards(filePath, 2);
            System.out.println(part2Sum);
        }

        if (day == 3) {
            Day03 day03 = new Day03();
            int part1Sum = day03.machineParts(filePath, 1);
            System.out.println(part1Sum);
            int part2Sum = day03.machineParts(filePath, 2);
            System.out.println(part2Sum);
        }

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

    /**
     * Helper method to create the file path for each puzzle input.
     */
    private static String buildFilePathForToday(int day, boolean fullInput) {
        StringBuilder sb = new StringBuilder();
        sb.append("src/main/resources/");
        sb.append(fullInput ? "input" : "test");
        sb.append("_day");
        sb.append(String.format("%02d", day));
        // System.out.println(sb);
        return sb.toString();
    }
}