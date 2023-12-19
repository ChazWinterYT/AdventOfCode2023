package com.chazwinter.util;

import java.util.List;

/* NOTE: This class is needed for Day05. */
public class RangeMapper {
    MapType mapType;
    long destStart;
    long sourceStart;
    long range;

    public RangeMapper(MapType mapType, long destStart, long sourceStart, long range) {
        this.mapType = mapType;
        this.destStart = destStart;
        this.sourceStart = sourceStart;
        this.range = range;
    }

    public static long getMapping(long startValue, List<RangeMapper> mappings) {
        for (RangeMapper mapper : mappings) {
            if (startValue >= mapper.sourceStart && startValue < mapper.sourceStart + mapper.range) {
                long offset = mapper.sourceStart - mapper.destStart;
                return startValue - offset;
            }
        }
        /* If the startValue is not covered by the Mapper, then it maps to itself. */
        return startValue;
    }

    @Override
    public String toString() {
        return String.format("%s: dest: %d, src: %d, range: %d.",
                mapType, destStart, sourceStart, range);
    }
}