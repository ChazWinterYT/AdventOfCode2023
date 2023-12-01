package com.chazwinter;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        Day01 day01 = new Day01();
        String filePath01 = "src/main/resources/input_day01";
        int part1Sum = day01.sumNumbersFromStrings( filePath01, 1);
        System.out.println(part1Sum);
        int part2Sum = day01.sumNumbersFromStrings(filePath01, 2);
        System.out.println(part2Sum);
    }
}