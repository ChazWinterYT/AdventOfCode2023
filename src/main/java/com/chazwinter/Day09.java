package com.chazwinter;

import com.chazwinter.util.AocUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day09 {
    public int predictReadings(String filePath, int part) {
        AtomicInteger sumOfReadings = new AtomicInteger(0);

        AocUtils.processInputFile(filePath, (line) -> {
            List<Integer> readings = AocUtils.extractIntegersFromString(line, Collectors.toList());
            if (part == 2) { // Part 2: Reverse the List to get the first reading instead of the last.
                Collections.reverse(readings);
            }
            int nextSum = getNextReading(readings);
            sumOfReadings.getAndAdd(nextSum);
        });

        return sumOfReadings.get();
    }

    /**
     * Recursive function to find the next reading in a List using the defined pattern.
     * @param readings List of readings that needs the next value.
     * @return The next value that should be in the List
     */
    private int getNextReading(List<Integer> readings) {
        // Base case: If the List consists of only one element, that's the next reading.
        if (readings.size() == 1 || onlyOneValueInList(readings)) {
            return readings.get(0);
        }
        List<Integer> nextLine = new ArrayList<>();
        for (int i = 0; i < readings.size() - 1; i++) {
            nextLine.add(readings.get(i + 1) - readings.get(i));
        }

        return readings.get(readings.size() - 1) + getNextReading(nextLine);
    }

    /**
     * Helper method to determine if a List consists of only one value repeated.
     * @param readings The List we want to check.
     * @return true if there is only one value repeating in the List, false otherwise.
     */
    private boolean onlyOneValueInList(List<Integer> readings) {
        int testValue = readings.get(0);
        for (int i = 1; i < readings.size(); i++) {
            if (readings.get(i) != testValue) {
                return false;
            }
        }
        return true;
    }
}
