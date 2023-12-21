package com.chazwinter;

import com.chazwinter.model.camelcardgame.Card;
import com.chazwinter.model.camelcardgame.Hand;
import com.chazwinter.model.camelcardgame.HandTypes;
import com.chazwinter.util.AocUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day07 {
    public int camelCards(String filePath, int part) throws IOException {
        // camelCardTest();     // Debug. Test to ensure that the Hands are built and sorted properly.

        List<Hand> allHands = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] cardsAndWager = line.split(" ");
            int wager = AocUtils.extractIntFromString(cardsAndWager[1]);
            allHands.add(new Hand(cardsAndWager[0], wager));
        }
        allHands.sort(Hand.getComparator());
        int rank = allHands.size();
        int totalWinnings = 0;
        for (Hand hand : allHands) {
            hand.setHandRank(rank--);
            hand.setWinnings(hand.getWager() * hand.getHandRank());
            totalWinnings += hand.getWinnings();
        }
        // Debug. Check the List of Hands to ensure they were generated and ranked properly.
        // testPrintHands(allHands);
        return totalWinnings;
    }

    /**
     * Test method to print the hands, one per line, to ensure they were parsed correctly.
     */
    private void testPrintHands(List<Hand> allHands) {
        for (Hand hand : allHands) {
            System.out.println(hand);
        }
    }

}
