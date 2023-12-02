package com.chazwinter.model;

import java.util.ArrayList;
import java.util.List;

/* Class needed for Day02 */
public class ColorGame {
    public static final int MAX_RED = 12;
    public static final int MAX_GREEN = 13;
    public static final int MAX_BLUE = 14;

    private int gameNumber;
    private List<Integer> reds;
    private List<Integer> greens;
    private List<Integer> blues;
    private boolean possible;

    public ColorGame (int gameNumber) {
        this.gameNumber = gameNumber;
        this.reds = new ArrayList<>();
        this.greens = new ArrayList<>();
        this.blues = new ArrayList<>();
    }

    public void extractGameData(String[] data) {
        int red = 0, green = 0, blue = 0;
        // Example input array: [(3 blue), (1 green)]. Colors may be missing.
        for (String d : data) {
            String[] numAndColor = d.split(" ");
            // Your array is now [, 6, blue].

            /* If a color appears in this draw, update its block count.
                Otherwise, it will stay zero. Either way, add the count to the List. */
            int blocks = Integer.parseInt(numAndColor[1]);
            switch(numAndColor[2]) {
                case "red" :
                    red = blocks;
                    break;
                case "green" :
                    green = blocks;
                    break;
                case "blue" :
                    blue = blocks;
                    break;
            }
        }
        this.reds.add(red);
        this.greens.add(green);
        this.blues.add(blue);
    }

    public boolean validateGameIndividual() {
        for (int red : reds) {
            if (red > MAX_RED) return false;
        }
        for (int green : greens) {
            if (green > MAX_GREEN) return false;
        }
        for (int blue : blues) {
            if (blue > MAX_BLUE) return false;
        }
        return true;
    }

    public int calculatePower() {
        int maxRed = 0, maxGreen = 0, maxBlue = 0;
        for (int red : reds) {
            maxRed = Math.max(maxRed, red);
        }
        for (int blue : blues) {
            maxBlue = Math.max(maxBlue, blue);
        }
        for (int green : greens) {
            maxGreen = Math.max(maxGreen, green);
        }
        return maxRed * maxBlue * maxGreen;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public List<Integer> getReds() {
        return reds;
    }

    public void setReds(List<Integer> reds) {
        this.reds = reds;
    }

    public List<Integer> getGreens() {
        return greens;
    }

    public void setGreens(List<Integer> greens) {
        this.greens = greens;
    }

    public List<Integer> getBlues() {
        return blues;
    }

    public void setBlues(List<Integer> blues) {
        this.blues = blues;
    }

    public boolean isPossible() {
        return possible;
    }

    public void setPossible(boolean possible) {
        this.possible = possible;
    }

    @Override
    public String toString() {
        return String.format("Game %d: Reds: %s, Greens: %s, Blues: %s.",
                gameNumber, reds, greens, blues);
    }
}
