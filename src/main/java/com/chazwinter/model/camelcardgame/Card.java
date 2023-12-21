package com.chazwinter.model.camelcardgame;

import java.util.HashMap;
import java.util.Map;

public class Card {
    private int rank;
    private char rankAsChar;
    private String suit;    // Not needed...yet?

    private static final Map<Character, Integer> CARD_RANK_MAP = new HashMap<>();
    static {
        CARD_RANK_MAP.put('2', 2);        CARD_RANK_MAP.put('3', 3);        CARD_RANK_MAP.put('4', 4);
        CARD_RANK_MAP.put('5', 5);        CARD_RANK_MAP.put('6', 6);        CARD_RANK_MAP.put('7', 7);
        CARD_RANK_MAP.put('8', 8);        CARD_RANK_MAP.put('9', 9);        CARD_RANK_MAP.put('T', 10);
        CARD_RANK_MAP.put('J', 11);       CARD_RANK_MAP.put('Q', 12);       CARD_RANK_MAP.put('K', 13);
        CARD_RANK_MAP.put('A', 14);
    }

    public Card (char rankAsChar) {
        this.rankAsChar = rankAsChar;
        this.rank = CARD_RANK_MAP.get(rankAsChar);
        this.suit = "";
    }

    public int getRank() {
        return rank;
    }

    public char getRankAsChar() {
        return rankAsChar;
    }

    @Override
    public String toString() {
        return this.rankAsChar + "";
    }
}
