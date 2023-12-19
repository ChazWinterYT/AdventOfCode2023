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
    public long seedFertilizer(String filePath, int part) throws IOException {
        Map<MapType, List<RangeMapper>> allTypeMappings = new HashMap<>();
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
                // The first split produces an empty String, so skip it. The others are Seeds.
                for (int i = 1; i < splitNumbers.length; i++) {
                    seeds.add(new Seed(Long.parseLong(splitNumbers[i])));
                }
            }
        }
        long minLocation = Long.MAX_VALUE;
        for (Seed seed : seeds) {
            // Convert a seed input to its Location via a series of mappings
            currentMapType = MapType.SEED_TO_SOIL;
            seed.setSoil(RangeMapper.getMapping(
                    seed.getSeedNumber(), allTypeMappings.get(currentMapType)));
            currentMapType = MapType.SOIL_TO_FERTILIZER;
            seed.setFertilizer(RangeMapper.getMapping(
                    seed.getSoil(), allTypeMappings.get(currentMapType)));
            currentMapType = MapType.FERTILIZER_TO_WATER;
            seed.setWater(RangeMapper.getMapping(
                    seed.getFertilizer(), allTypeMappings.get(currentMapType)));
            currentMapType = MapType.WATER_TO_LIGHT;
            seed.setLight(RangeMapper.getMapping(
                    seed.getWater(), allTypeMappings.get(currentMapType)));
            currentMapType = MapType.LIGHT_TO_TEMPERATURE;
            seed.setTemperature(RangeMapper.getMapping(
                    seed.getLight(), allTypeMappings.get(currentMapType)));
            currentMapType = MapType.TEMPERATURE_TO_HUMIDITY;
            seed.setHumidity(RangeMapper.getMapping(
                    seed.getTemperature(), allTypeMappings.get(currentMapType)));
            currentMapType = MapType.HUMIDITY_TO_LOCATION;
            seed.setLocation(RangeMapper.getMapping(
                    seed.getHumidity(), allTypeMappings.get(currentMapType)));
            minLocation = Math.min(minLocation, seed.getLocation());
            // Debug, uncomment to see this seed's attributes.
            // System.out.println(seed);
        }
        if (part == 1) {
            return minLocation;
        } else {    // Part 2
            return -1;
        }
    }

    private MapType getCurrentMapTypeFromLine(String line) {
        String[] splitLine = line.split(" ");
        String mapTypeAsString = splitLine[0]
                .toUpperCase()
                .replace("-", "_");
        return MapType.valueOf(mapTypeAsString);
    }


}
