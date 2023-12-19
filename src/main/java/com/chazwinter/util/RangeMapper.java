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

    /**
     * Given an input value, figure out what output value it maps to.
     * @param startValue The input value.
     * @param mappings The List of RangeMapper mappings that could apply to this input value.
     * @return The mapped output value.
     */
    public static long getMapping(long startValue, List<RangeMapper> mappings) {
        for (RangeMapper mapper : mappings) {
            /* If the start value falls within the range of one of the mappers, use it! */
            if (startValue >= mapper.sourceStart && startValue < mapper.sourceStart + mapper.range) {
                long offset = mapper.sourceStart - mapper.destStart;
                return startValue - offset;
            }
        }
        /* If the startValue is not covered by the Mapper, then the value maps to itself. */
        return startValue;
    }

    @Override
    public String toString() {
        return String.format("%s: dest: %d, src: %d, range: %d.",
                mapType, destStart, sourceStart, range);
    }
}