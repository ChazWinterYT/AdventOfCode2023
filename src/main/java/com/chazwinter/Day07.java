package com.chazwinter;

import com.chazwinter.model.camelcardgame.Hand;
import com.chazwinter.util.AocUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day07 {
    public int camelCards(String filePath, int part) throws IOException {
        List<Hand> allHands = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] cardsAndWager = line.split(" ");
            String handAsString = cardsAndWager[0];
            int wager = AocUtils.extractIntFromString(cardsAndWager[1]);
            int numJokers = 0;
            // Only Part 2 uses Jokers. Replace them with a new symbol, so they can be evaluated properly.
            if (part == 2) {
                handAsString = handAsString.replace('J', '?');
                numJokers = Hand.countJokers(handAsString);
            }
            Hand hand = new Hand(handAsString, wager, numJokers);
            allHands.add(hand);
        }

        allHands.sort(Hand.HAND_COMPARATOR);
        int rank = allHands.size();
        int totalWinnings = 0;
        for (Hand hand : allHands) {
            hand.setHandRank(rank--); // Decrement the rank for each card you see.
            hand.setWinnings(hand.getWager() * hand.getHandRank());
            totalWinnings += hand.getWinnings();
        }
        // Debug. Check the List of Hands to ensure they were generated and ranked properly.
        // testPrintHands(allHands);
        return totalWinnings;
    }

    /**
     * Test method to print the hands, one per line, to ensure they were evaluated correctly.
     */
    private void testPrintHands(List<Hand> allHands) {
        for (Hand hand : allHands) {
            System.out.println(hand);
        }
    }

}
