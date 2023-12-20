package com.chazwinter;

import com.chazwinter.util.AocUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 {
    public int boatRace(String filePath, int part) throws IOException {
        List<Integer> timeValues;
        List<Integer> distanceValues;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] splitNums = line.split("\\s+");
            for (String s : splitNums) {
                if (AocUtils.isNumeric(s)) {
                    if (line.startsWith("Time:")) {
                        timeValues = AocUtils.extractNumbersFromString(s, Collectors.toList());
                    } else if (line.startsWith("Distance:")) {
                        distanceValues = AocUtils.extractNumbersFromString(s, Collectors.toList());
                    }
                }
            }
        }

        return -1;
    }

}
