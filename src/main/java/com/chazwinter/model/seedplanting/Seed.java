package com.chazwinter.model.seedplanting;

/* NOTE: This class is needed for Day05. */
public class Seed {
    private long seedNumber;
    private long soil;
    private long fertilizer;
    private long water;
    private long light;
    private long temperature;
    private long humidity;
    private long location;
    private long numSeedsInSequence;

    public Seed(long value, String attribute) {
        if (attribute.equals("location")) {
            this.location = value;
        } else if (attribute.equals("seedNumber")) {
            this.seedNumber = value;
        }
    }

    public long getSeedNumber() {
        return seedNumber;
    }

    public void setSeedNumber(long seedNumber) {
        this.seedNumber = seedNumber;
    }

    public long getSoil() {
        return soil;
    }

    public void setSoil(long soil) {
        this.soil = soil;
    }

    public long getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(long fertilizer) {
        this.fertilizer = fertilizer;
    }

    public long getWater() {
        return water;
    }

    public void setWater(long water) {
        this.water = water;
    }

    public long getLight() {
        return light;
    }

    public void setLight(long light) {
        this.light = light;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public long getNumSeedsInSequence() {
        return numSeedsInSequence;
    }

    public void setNumSeedsInSequence(long numSeedsInSequence) {
        this.numSeedsInSequence = numSeedsInSequence;
    }

    @Override
    public String toString() {
        return String.format("Seed #%d: Soil %d, Fer %d, Wtr %d, Lgt %d, Tmp %d, Hum %d, Loc %d. %d Seeds in seq.",
                seedNumber, soil, fertilizer, water, light, temperature, humidity, location, numSeedsInSequence);
    }
}
