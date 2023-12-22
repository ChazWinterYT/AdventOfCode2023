package com.chazwinter.model.camelcardgame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Hand {
    private final List<Card> cards;
    private final int wager;
    private final int numJokers;  // Used in part 2.

    private Map<Integer, Integer> cardFrequencies;
    private Set<Integer> cardFrequencySet;
    private HandTypes handType;
    private int handRank;   // Calculated once all Hands are generated.
    private int winnings;   // Calculated once all Hands are ranked.

    /**
     * Constructor for when you want to go from a String directly to a Hand of Cards.
     * @param originalHandAsString The String representation of the cards in a Hand.
     * @param wager The wager associated with that String of Cards.
     */
    public Hand(String originalHandAsString, int wager, int numJokers) {
        this(stringToCardList(originalHandAsString), wager, numJokers);
    }

    /**
     * Constructor for when you already have the List of Cards.
     * Calls additional methods to calculate other class fields.
     * @param cards The List of Cards in this Hand.
     * @param wager The wager associated with that List of Cards.
     */
    public Hand(List<Card> cards, int wager, int numJokers) {
        this.cards = cards;
        this.wager = wager;
        this.numJokers = numJokers;
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

    /**
     * For Part 2. Counts the Jokers in the Hand's String representation.
     * @param cardsAsString The String representation of the Hand to be checked.
     * @return the number of Jokers in the Hand.
     */
    public static int countJokers(String cardsAsString) {
        int jokers = 0;
        for (char c : cardsAsString.toCharArray()) {
            if (c == '?') {
                jokers++;
            }
        }
        return jokers;
    }

    private void calculateCardFrequencies() {
        this.cardFrequencies = new HashMap<>();
        this.cardFrequencySet = new HashSet<>();
        for (Card card : cards) {
            if (card.getRankAsChar() == '?') continue; // Don't count Jokers.
            cardFrequencies.merge(card.getRank(), 1, Integer::sum);
        }
        cardFrequencySet.addAll(cardFrequencies.values());
    }

    public void calculateHandType() {
        for (HandTypes handType : HandTypes.values()) {
            if (handType.matches(this)) {
                this.handType = handType;
                return;
            }
        }
    }

    /**
     * Comparator to be used to rank Hands via tie-breaker. Ranks are in descending order, so compare() method
     * returns -1 if the first hand is stronger, 1 if the second hand is stronger, 0 if they are equal.
     */
    public static final Comparator<Hand> HAND_COMPARATOR = (h1, h2) -> {
        // First rank by stronger HandType. HandTypes are already ranked by strength within the enum.
        for (HandTypes handType : HandTypes.values()) {
            if (h1.handType.equals(handType) && !h2.handType.equals(handType)) {
                return -1;
            } else if (!h1.handType.equals(handType) && h2.handType.equals(handType)) {
                return 1;
            } else if (h1.handType.equals(handType) && h2.handType.equals(handType)) {
                break;  // Go to tie-breaker.
            }
        }
        /* Tie-breaker. Used if the hands have the same HandType. */
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
    };

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public int getWager() {
        return wager;
    }

    public Map<Integer, Integer> getCardFrequencies() {
        return cardFrequencies;
    }

    public Set<Integer> getCardFrequencySet() {
        return cardFrequencySet;
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

    public int getNumJokers() {
        return numJokers;
    }

    @Override
    public String toString() {
        String cardListAsString = cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(""));
        return String.format("%s. %s. Jokers: %d. Wager: %d. Rank: %d, Winnings: %d",
                cardListAsString, handType, numJokers, wager, handRank, winnings);
    }
}
