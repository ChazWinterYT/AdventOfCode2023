package com.chazwinter;

import com.chazwinter.model.seedplanting.Seed;
import com.chazwinter.util.AocUtils;
import com.chazwinter.model.seedplanting.MapType;
import com.chazwinter.model.seedplanting.RangeMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/* NOTE: This class uses classes in the model.seedplanting package. */
public class Day05 {
    Map<MapType, List<RangeMapper>> allTypeMappings;
    Map<Long, Long> validSeeds;

    public long seedFertilizer(String filePath, int part) {
        allTypeMappings = new HashMap<>();
        validSeeds = new HashMap<>();

        AtomicReference<MapType> currentMapType = new AtomicReference<>();
        AocUtils.processInputFile(filePath, (line) -> {
            // Find the word "map": Get the MapType and associate an ArrayList with it.
            if (line.contains("map:")) {
                currentMapType.set(getCurrentMapTypeFromLine(line));
                allTypeMappings.putIfAbsent(currentMapType.get(), new ArrayList<>());
                /* Oh yeah, we need the seeds too. */
            } else if (line.contains("seeds:")) {
                String[] splitByColon = line.split(":");
                String[] splitNumbers = splitByColon[1].split(" ");
                if (part == 1) {
                    // The first split produces an empty String, so skip it. The others are Seeds.
                    for (int i = 1; i < splitNumbers.length; i++) {
                        validSeeds.put(AocUtils.extractLongFromString(splitNumbers[i]), 1L);
                    }
                } else if (part == 2) {
                    // Now they are pairs denoting a starting seed number, and a range of consecutive seeds.
                    for (int i = 1; i < splitNumbers.length; i++) {
                        validSeeds.put(AocUtils.extractLongFromString(splitNumbers[i]),
                                AocUtils.extractLongFromString(splitNumbers[++i]));
                    }
                }
                /* Find numbers: Create a RangeMapper with those numbers and put it in the Map for that MapType. */
            } else if (!line.trim().isEmpty()) {
                String[] valuesToMap = line.split(" ");
                RangeMapper mapper = new RangeMapper(
                        currentMapType.get(),
                        Long.parseLong(valuesToMap[0]),
                        Long.parseLong(valuesToMap[1]),
                        Long.parseLong(valuesToMap[2]));
                allTypeMappings.get(currentMapType.get()).add(mapper);
            }
        });
        long minLocation;
        if (part == 1) {
            minLocation = Long.MAX_VALUE;
        } else {    // Part 2.
            minLocation = 0;
        }
        /* For Part 1, it is faster to iterate through the established Set of Seeds to find the lowest location.
         * For Part 2, it is actually faster to iterate from location 0, and brute-force check every location until
         * you generate a valid Seed from the Set. The first location that results in a valid Seed is
         * inherently the lowest. */
        if (part == 1) {
            for (long seedNumber : validSeeds.keySet()) {
                Seed seed = new Seed(seedNumber, "seedNumber");
                long seedLocation = processSeed(seed);
                minLocation = Math.min(minLocation, seedLocation);
            }
        } else if (part == 2) {
            // minLocation cap is arbitrary. In this case I know the answer is less than 3,000,000.
            // The cap was used during testing to ensure I wouldn't accidentally cause an infinite loop.
            while (!isValidSeed(processSeedFromLocation(minLocation)) && minLocation < 3_000_000) {
                minLocation++;
            }
        }
        return minLocation;
    }

    /**
     * Read the input file, and figure out which enum corresponds to this line.
     * @param line The line from the input file.
     * @return The enum MapType for that line.
     */
    private MapType getCurrentMapTypeFromLine(String line) {
        String[] splitLine = line.split(" ");
        String mapTypeAsString = splitLine[0]
                .toUpperCase()
                .replace("-", "_");
        return MapType.valueOf(mapTypeAsString);
    }

    /**
     * Part 1. Using all the various RangeMappers, figure out the rest of the Seed's attributes.
     * @param seed The input seed that needs attributes.
     * @return The location attribute, since that's what we're going to use to solve the problem.
     */
    private long processSeed(Seed seed) {
        MapType currentMapType = MapType.SEED_TO_SOIL;
        seed.setSoil(RangeMapper.getMapping(seed.getSeedNumber(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.SOIL_TO_FERTILIZER;
        seed.setFertilizer(RangeMapper.getMapping(seed.getSoil(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.FERTILIZER_TO_WATER;
        seed.setWater(RangeMapper.getMapping(seed.getFertilizer(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.WATER_TO_LIGHT;
        seed.setLight(RangeMapper.getMapping(seed.getWater(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.LIGHT_TO_TEMPERATURE;
        seed.setTemperature(RangeMapper.getMapping(seed.getLight(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.TEMPERATURE_TO_HUMIDITY;
        seed.setHumidity(RangeMapper.getMapping(seed.getTemperature(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.HUMIDITY_TO_LOCATION;
        seed.setLocation(RangeMapper.getMapping(seed.getHumidity(), allTypeMappings.get(currentMapType)));
        return seed.getLocation();
    }

    /**
     *  Part 2. Using all the various RangeMappers, figure out the rest of the Seed's attributes, starting from the
     *  last attribute.
     * @param location The location we're going to try for building a valid seed
     * @return The seed number that would end up at that location.
     */
    private long processSeedFromLocation(long location) {
        Seed seed = new Seed(location, "location");
        MapType currentMapType = MapType.HUMIDITY_TO_LOCATION;
        seed.setHumidity(RangeMapper.getMappingReversed(seed.getLocation(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.TEMPERATURE_TO_HUMIDITY;
        seed.setTemperature(RangeMapper.getMappingReversed(seed.getHumidity(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.LIGHT_TO_TEMPERATURE;
        seed.setLight(RangeMapper.getMappingReversed(seed.getTemperature(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.WATER_TO_LIGHT;
        seed.setWater(RangeMapper.getMappingReversed(seed.getLight(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.FERTILIZER_TO_WATER;
        seed.setFertilizer(RangeMapper.getMappingReversed(seed.getWater(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.SOIL_TO_FERTILIZER;
        seed.setSoil(RangeMapper.getMappingReversed(seed.getFertilizer(), allTypeMappings.get(currentMapType)));
        currentMapType = MapType.SEED_TO_SOIL;
        seed.setSeedNumber(RangeMapper.getMappingReversed(seed.getSoil(), allTypeMappings.get(currentMapType)));
        return seed.getSeedNumber();
    }

    /**
     * Part 2. Determine if the location we used to generate a Seed resulted in a valid Seed.
     * @param seedNumber the number corresponding to a potential Seed.
     * @return true if the Seed is valid (it's in the initial Set of Seeds), false otherwise.
     */
    private boolean isValidSeed(long seedNumber) {
        for (Long x : validSeeds.keySet()) {
            if (seedNumber >= x && seedNumber < x + validSeeds.get(x)) {
                return true;
            }
        }
        return false;
    }
}
