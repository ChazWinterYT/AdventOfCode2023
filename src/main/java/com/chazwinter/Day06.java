package com.chazwinter;

import com.chazwinter.util.AocUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 {
    public long boatRace(String filePath, int part) throws IOException {
        List<Long> timeValues = new ArrayList<>();
        List<Long> distanceValues = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (part == 1) {
                if (line.startsWith("Time:")) {
                    timeValues = AocUtils.extractLongsFromString(line, Collectors.toList());
                } else if (line.startsWith("Distance:")) {
                    distanceValues = AocUtils.extractLongsFromString(line, Collectors.toList());
                }
            } else { // Part 2: It's actually only one number. Whatever, just add the single number to the List.
                if (line.startsWith("Time:")) {
                    timeValues.add(AocUtils.extractLongFromString(line.replace(" ", "")));
                } else if (line.startsWith("Distance:")) {
                    distanceValues.add(AocUtils.extractLongFromString(line.replace(" ", "")));
                }
            }
        }
        /* Get the upper and lower bounds of valid times.
        * In part two, the lists only contain one value. */
        return getWaysToBeatTheRecord(timeValues, distanceValues);
    }

    /**
     * Method that solves Part 1. Find the total different ways to win by beating the record in each race.
     * @param timeValues The time limits of each race.
     * @param distanceValues The distance values to beat in each race.
     * @return The total ways you can win by beating the records in each race.
     */
    private long getWaysToBeatTheRecord(List<Long> timeValues, List<Long> distanceValues) {
        long waysToBeatTheRecord = 1;
        for (int i = 0; i < timeValues.size(); i++) {
            long timeLimit = timeValues.get(i);
            long distanceToBeat = distanceValues.get(i);
            long lowerBound = getLowerBound(timeLimit, distanceToBeat);
            long upperBound = timeLimit - lowerBound; // Read the comment at the bottom of this class for an explanation.
            waysToBeatTheRecord *= (upperBound - lowerBound + 1);
        }
        return waysToBeatTheRecord;
    }

    /**
     * Get the lowest possible time that still beats the target time.
     * @param timeLimit Overall time you have for the race.
     * @param distanceToBeat distance that must be exceeded.
     * @return The smallest value that still wins.
     */
    private long getLowerBound(long timeLimit, long distanceToBeat) {
        long left = 0, right = timeLimit / 2;
        while (left < right) {
            long mid = left + (right - left) / 2;
            long currentDistance = mid * (timeLimit - mid);
            if (currentDistance <= distanceToBeat) { // Not a winning time - it's too small.
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    /* Okay, so suppose your time was 30 and the lowerBound to win was 11.
    * The times are calculated by dist * (time - dist). Which is dist * time - dist^2.
    * So it's an upside-down parabola, where the two ends are zero, and the max is at the midpoint.
    * That means when you find the lowerBound, you know where the upperBound is.
    * The upperBound and lowerBound will add up to the total time.  So if one is 11, the other is 19. */
}