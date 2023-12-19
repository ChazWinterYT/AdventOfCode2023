package com.chazwinter;

import com.chazwinter.model.Seed;
import com.chazwinter.util.MapType;
import com.chazwinter.util.RangeMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* NOTE: This class uses the model.Seed class. */
/* NOTE: This class uses the util.RangeMapper class. */
/* NOTE: This class uses the util.MapType enum. */
public class Day05 {
    Map<MapType, List<RangeMapper>> allTypeMappings;

    public long seedFertilizer(String filePath, int part) throws IOException {
        allTypeMappings = new HashMap<>();
        List<Seed> seeds = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        MapType currentMapType = null;
        while ((line = reader.readLine()) != null) {
            // Find the word "map": Get the MapType and associate an ArrayList with it.
            if (line.contains("map:")) {
                currentMapType = getCurrentMapTypeFromLine(line);
                allTypeMappings.putIfAbsent(currentMapType, new ArrayList<>());
            /* Find numbers: Create a RangeMapper with those numbers and put it in the Map for that MapType. */
            } else if (currentMapType != null && !line.trim().isEmpty()) {
                String[] valuesToMap = line.split(" ");
                RangeMapper mapper = new RangeMapper(
                        currentMapType,
                        Long.parseLong(valuesToMap[0]),
                        Long.parseLong(valuesToMap[1]),
                        Long.parseLong(valuesToMap[2]));
                allTypeMappings.get(currentMapType).add(mapper);
            } else if (line.contains("seeds:")) {
                String[] splitByColon = line.split(":");
                String[] splitNumbers = splitByColon[1].split(" ");
                if (part == 1) {
                    // The first split produces an empty String, so skip it. The others are Seeds.
                    for (int i = 1; i < splitNumbers.length; i++) {
                        seeds.add(new Seed(Long.parseLong(splitNumbers[i])));
                    }
                } else if (part == 2) {
                    // Now they are pairs denoting a starting seed number, and a range of consecutive seeds.
                    for (int i = 1; i < splitNumbers.length; i++) {
                        Seed seed = new Seed(Long.parseLong(splitNumbers[i]));
                        seed.setNumSeedsInSequence(Long.parseLong(splitNumbers[++i]));
                        seeds.add(seed);
                    }
                }
            }
        }
        long minLocation = Long.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i++) {
            long seedLocation = Long.MAX_VALUE;
            if (part == 1) {
                Seed seed = seeds.get(i);
                seedLocation = processSeed(seed);
                minLocation = Math.min(minLocation, seedLocation);
            } else if (part == 2) {
                long startingSeed = seeds.get(i).getSeedNumber();
                long numSeeds = seeds.get(i).getNumSeedsInSequence();
                for (int j = 0; j < numSeeds; j++) {
                    seedLocation = processSeed(new Seed(startingSeed + j));
                    minLocation = Math.min(minLocation, seedLocation);
                }
            }
        }
        return minLocation;
    }

    private MapType getCurrentMapTypeFromLine(String line) {
        String[] splitLine = line.split(" ");
        String mapTypeAsString = splitLine[0]
                .toUpperCase()
                .replace("-", "_");
        return MapType.valueOf(mapTypeAsString);
    }

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
}
