package com.chazwinter;

import com.chazwinter.model.camelcardgame.Card;
import com.chazwinter.model.camelcardgame.Hand;
import com.chazwinter.model.camelcardgame.HandTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day07 {
    public int camelCards(String filePath, int part) {
        String testHandString1 = "QAAQQ";
        String testHandString2 = "9KK99";
        List<Card> testCards1 = new ArrayList<>();
        List<Card> testCards2 = new ArrayList<>();
        List<Hand> testHands = new ArrayList<>();
        for (char c : testHandString1.toCharArray()) {
            testCards1.add(new Card(c));
        }
        for (char c : testHandString2.toCharArray()) {
            testCards2.add(new Card(c));
        }
        Hand testHand1 = new Hand(testCards1, 1);
        Hand testHand2 = new Hand(testCards2, 2);
        testHands.add(testHand1);
        testHands.add(testHand2);
        Collections.sort(testHands, Hand.getComparator());
        System.out.println(testHands);

        return -1;
    }
}
