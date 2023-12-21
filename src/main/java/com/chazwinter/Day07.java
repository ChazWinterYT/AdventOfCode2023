package com.chazwinter;

import com.chazwinter.model.camelcardgame.Card;
import com.chazwinter.model.camelcardgame.Hand;
import com.chazwinter.model.camelcardgame.HandTypes;

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

        }
        return -1;
    }

    /**
     * Just a test to make sure my card ranking algorithm actually works.
     */
    public void camelCardTest() {
        String testHandString1 = "AAAQQ";
        String testHandString2 = "KK599";
        String testHandString3 = "A333A";
        List<Hand> testHands = new ArrayList<>();
        Hand testHand1 = new Hand(testHandString1, 99);
        Hand testHand2 = new Hand(testHandString2, 66);
        Hand testHand3 = new Hand(testHandString3, 55);
        testHands.add(testHand1);
        testHands.add(testHand2);
        testHands.add(testHand3);
        testHands.sort(Hand.getComparator());
        System.out.println(testHands.get(0));
        System.out.println(testHands.get(1));
        System.out.println(testHands.get(2));
    }
}
