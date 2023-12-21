package com.chazwinter.model.camelcardgame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand {
    private List<Card> cards;
    private int wager;
    private Map<Integer, Integer> cardFrequencies;
    private HandTypes handType;
    private int handRank;

    public Hand(List<Card> cards, int wager) {
        this.cards = cards;
        this.wager = wager;
        calculateCardFrequencies();
        calculateHandType();
    }

    private void calculateCardFrequencies() {
        this.cardFrequencies = new HashMap<>();
        for (Card card : cards) {
            cardFrequencies.merge(card.getRank(), 1, Integer::sum);
        }
    }

    public HandTypes calculateHandType() {
        for (HandTypes handType : HandTypes.values()) {
            if (handType.matches(this)) {
                this.handType = handType;
                return handType;
            }
        }
        return HandTypes.HIGH_CARD;
    }

    /**
     * Comparator to be used to rank Hands via tie-breaker.
     * @return 1 if the first hand is stronger, -1 if the second hand is stronger, 0 if they are equal.
     */
    public static Comparator<Hand> getComparator() {
        return new Comparator<Hand>() {
            @Override
            public int compare(Hand h1, Hand h2) {
                // First rank by stronger HandType.
                for (HandTypes handType : HandTypes.values()) {
                    if (handType.matches(h1) && !handType.matches(h2)) {
                        return -1;
                    } else if (!handType.matches(h1) && handType.matches(h2)) {
                        return 1;
                    } else if (handType.matches(h1) && handType.matches(h2)) {
                        break;
                    }
                }
                // Then use tie-breaker for same HandType.
                List<Card> h1Cards = h1.cards;
                List<Card> h2Cards = h2.cards;
                for (int i = 0; i < h1Cards.size(); i++) {
                    if (h1Cards.get(i).getRank() > h2Cards.get(i).getRank()) {
                        return -1;
                    } else if (h1Cards.get(i).getRank() < h2Cards.get(i).getRank()) {
                        return 1;
                    }
                }
                return 0;
            }
        };
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public int getWager() {
        return wager;
    }

    public Map<Integer, Integer> getCardFrequencies() {
        return cardFrequencies;
    }

    public HandTypes getHandType() {
        return handType;
    }

    @Override
    public String toString() {
        String cardString = cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(""));
        return String.format("%s. %s. w: %d",
                cardString, handType, wager);
    }
}
