package com.chazwinter.model.camelcardgame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand {
    private String originalHandAsString;
    private List<Card> cards;
    private int wager;
    private int numJokers;  // Used in part 2.

    private Map<Integer, Integer> cardFrequencies;
    private HandTypes handType;
    private int handRank;   // Calculated once all Hands are generated.
    private int winnings;   // Calculated once all Hands are ranked.

    /**
     * Constructor for when you want to go from a String directly to a Hand of Cards.
     * @param originalHandAsString The String representation of the cards in a Hand.
     * @param wager The wager associated with that String of Cards.
     */
    public Hand(String originalHandAsString, int wager, int numJokers) {
        this(stringToCardList(originalHandAsString), originalHandAsString, wager, numJokers);
    }

    /**
     * Constructor for when you already have the List of Cards.
     * Calls additional methods to calculate other class fields.
     * @param cards The List of Cards in this Hand.
     * @param wager The wager associated with that List of Cards.
     */
    public Hand(List<Card> cards, String originalHandAsString, int wager, int numJokers) {
        this.cards = cards;
        this.originalHandAsString = originalHandAsString;
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
            if (c == '?') continue;    // Don't put Jokers in the Hand.
            Card card = new Card(c);
            cards.add(card);
        }
        return cards;
    }

    /**
     * For Part 2. Checks for Jokers, counts them, and removes them from the Hand's String representation.
     * @param cardsAsString The String representation of the Hand to be checked.
     * @return the number of Jokers in the Hand.
     */
    public static int countJokers(String cardsAsString) {
        int numJokers = 0;
        for (char c : cardsAsString.toCharArray()) {
            if (c == '?') {
                numJokers++;
            }
        }
        return numJokers;
    }

    private void calculateCardFrequencies() {
        this.cardFrequencies = new HashMap<>();
        for (Card card : cards) {
            cardFrequencies.merge(card.getRank(), 1, Integer::sum);
        }
    }

    public HandTypes calculateHandType() {
        for (HandTypes handType : HandTypes.values()) {
            if (handType.matchesJoker(this)) {
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
                /* Tie-breaker. Used if the hands have the same HandType. Must use the original String,
                *   because in Part 2, we removed the Jokers from the List of Cards in each Hand. */
                char[] h1OriginalCards = h1.originalHandAsString.toCharArray();
                char[] h2OriginalCards = h2.originalHandAsString.toCharArray();
                for (int i = 0; i < h1OriginalCards.length; i++) {
                    /* Turn the char into a Card, so it can be ranked correctly.
                    *   Cards are ranked in the order that they appear, not by strongest-first. */
                    Card card1 = new Card(h1OriginalCards[i]);
                    Card card2 = new Card(h2OriginalCards[i]);
                    if (card1.getRank() > card2.getRank()) {
                        return -1;
                    } else if (card1.getRank() < card2.getRank()) {
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

    public int getNumJokers() {
        return numJokers;
    }

    public void setNumJokers(int numJokers) {
        this.numJokers = numJokers;
    }

    @Override
    public String toString() {
        String cardListAsString = cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(""));
        return String.format("%s (%s). %s. Jokers: %d. Wager: %d. Rank: %d, Winnings: %d",
                cardListAsString, originalHandAsString, handType, numJokers, wager, handRank, winnings);
    }
}
