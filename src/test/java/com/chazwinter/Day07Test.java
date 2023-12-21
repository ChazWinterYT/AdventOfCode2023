package com.chazwinter;

import com.chazwinter.model.camelcardgame.Card;
import com.chazwinter.model.camelcardgame.Hand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class Day07Test {
    List<Card> testCards;
    List<Hand> testHands;

    @BeforeEach
    void setUp() {
        testCards = new ArrayList<>();
        testHands = new ArrayList<>();
    }

    @Test
    public void camelCards_threeValidHandsAllHighCard_ranksThemCorrectlyByCardOrder() {
        // GIVEN
        String testHandString1 = "762AK";   // should be 2nd
        String testHandString2 = "76543";   // should be 1st
        String testHandString3 = "3AQJT";   // should be 3rd
        Hand testHand1 = new Hand(testHandString1, 99);
        Hand testHand2 = new Hand(testHandString2, 66);
        Hand testHand3 = new Hand(testHandString3, 55);
        // WHEN
        testHands.add(testHand1);
        testHands.add(testHand2);
        testHands.add(testHand3);
        testHands.sort(Hand.getComparator());
        // THEN
        assertEquals(testHand2, testHands.get(0), "1st ranked hand should be 76543.");
        assertEquals(testHand1, testHands.get(1), "2nd ranked hand should be 762AK.");
        assertEquals(testHand3, testHands.get(2), "3rd ranked hand should be 3AQJT.");
    }

    @Test
    public void camelCards_threeValidHands_ranksThemCorrectlyWithTiebreaker() {
        // GIVEN
        String testHandString1 = "A333A";   // FULL_HOUSE, should be 2nd
        String testHandString2 = "KK599";   // TWO_PAIR, should be 3rd
        String testHandString3 = "AAAQQ";   // FULL_HOUSE, should be 1st
        Hand testHand1 = new Hand(testHandString1, 99);
        Hand testHand2 = new Hand(testHandString2, 66);
        Hand testHand3 = new Hand(testHandString3, 55);
        // WHEN
        testHands.add(testHand1);
        testHands.add(testHand2);
        testHands.add(testHand3);
        testHands.sort(Hand.getComparator());
        // THEN
        assertEquals(testHand3, testHands.get(0), "1st ranked hand should be AAAQQ.");
        assertEquals(testHand1, testHands.get(1), "2nd ranked hand should be A333A.");
        assertEquals(testHand2, testHands.get(2), "3rd ranked hand should be KK599.");
    }
}
