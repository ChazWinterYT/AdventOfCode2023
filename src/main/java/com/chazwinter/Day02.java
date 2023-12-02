package com.chazwinter;

import com.chazwinter.model.ColorGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day02 {
    public int validGameTotal(String filePath, int part) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int gameSum = 0, powerSum = 0;

        while ((line = reader.readLine()) != null) {
            String[] splitByColon = line.split(":");
            // Your array is now [gameNumber, (all draws for that game)].
            int gameNumber = Integer.parseInt(
                    splitByColon[0].replaceAll("[^0-9]", "").trim());

            ColorGame game = new ColorGame(gameNumber);
            String[] splitBySemicolon = splitByColon[1].split(";");
            // Your array is now [(3 blue, 1 green), (2 red, 1 blue, 1 green), etc].
            for (String draw : splitBySemicolon) {
                String[] splitByComma = draw.split(",");
                // Your array is now [(3 blue), (1 green)].
                game.extractGameData(splitByComma);
            }
            /* You now have a single ColorGame, containing a gameNumber, and
            * three Integer Lists: red blocks from each draw, blue blocks, and green blocks.
            */
            if (part == 1) {
                if (game.validateGameIndividual()) {
                    gameSum += game.getGameNumber();
                }
            } else if (part == 2) {
                powerSum += game.calculatePower();
            }
            //System.out.println(game);
        }
        return part == 1 ? gameSum : powerSum;
    }
}
