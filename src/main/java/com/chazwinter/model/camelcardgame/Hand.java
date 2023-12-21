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
    private int handRank;   // Calculated once all Hands are generated.
    private int winnings;   // Calculated once all Hands are ranked.

    /**
     * Constructor for when you want to go from a String directly to a Hand of Cards.
     * @param cardsAsString The String representation of the cards in a Hand.
     * @param wager The wager associated with that String of Cards.
     */
    public Hand(String cardsAsString, int wager) {
        this(stringToCardList(cardsAsString), wager);
    }

    /**
     * Constructor for when you already have the List of Cards.
     * Calls additional methods to calculate other class fields.
     * @param cards The List of Cards in this Hand.
     * @param wager The wager associated with that List of Cards.
     */
    public Hand(List<Card> cards, int wager) {
        this.cards = cards;
        this.wager = wager;
        calculateCardFrequencies();
        calculateHandType();
    }

    /**
     * Used by the Constructor that takes a String, to convert it to a List<Cards> before passing
     * it to the other Constructor.
     * @param cardsAsString The String representation of the cards in a Hand.
     * @return The List of Cards for this Hand.
     */
    private static List<Card> stringToCardList(String cardsAsString) {
        // Build cards using the String, then make the List of Cards.
        List<Card> cards = new ArrayList<>();
        for (char c : cardsAsString.toCharArray()) {
            Card card = new Card(c);
            cards.add(card);
        }
        return cards;
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
     * Comparator to be used to rank Hands via tie-breaker. Ranks are in descending order, so...
     * @return -1 if the first hand is stronger, 1 if the second hand is stronger, 0 if they are equal.
     */
    public static Comparator<Hand> getComparator() {
        return new Comparator<Hand>() {
            @Override
            public int compare(Hand h1, Hand h2) {
                // First rank by stronger HandType.
                for (HandTypes handType : HandTypes.values()) {
                    if (h1.handType.equals(handType) && !h2.handType.equals(handType)) {
                        return -1;
                    } else if (!h1.handType.equals(handType) && h2.handType.equals(handType)) {
                        return 1;
                    } else if (h1.handType.equals(handType) && h2.handType.equals(handType)) {
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

    public int getHandRank() {
        return handRank;
    }

    public void setHandRank(int handRank) {
        this.handRank = handRank;
    }

    public int getWinnings() {
        return winnings;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    @Override
    public String toString() {
        String cardString = cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(""));
        return String.format("%s. %s. wager: %d. Rank: %d, Winnings: %d",
                cardString, handType, wager, handRank, winnings);
    }
}
