package com.chazwinter.model.camelcardgame;

public enum HandTypes {
    FIVE_OF_A_KIND {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(5);
        }
        @Override
        public boolean matchesJoker (Hand hand) {
            if (hand.getNumJokers() == 0) return matches(hand);
            return hand.getNumJokers() > 3
                    || hand.getNumJokers() == 3 && hand.getCardFrequencies().containsValue(2)
                    || hand.getNumJokers() == 2 && hand.getCardFrequencies().containsValue(3)
                    || hand.getNumJokers() == 1 && hand.getCardFrequencies().containsValue(4);
        }
    },
    FOUR_OF_A_KIND {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(4);
        }
        @Override
        public boolean matchesJoker (Hand hand) {
            if (hand.getNumJokers() == 0) return matches(hand);
            return hand.getNumJokers() > 2
                    || hand.getNumJokers() == 2 && hand.getCardFrequencies().containsValue(2)
                    || hand.getNumJokers() == 1 && hand.getCardFrequencies().containsValue(3);
        }
    },
    FULL_HOUSE {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(3)
                    && hand.getCardFrequencies().containsValue(2);
        }
        @Override
        public boolean matchesJoker (Hand hand) {
            if (hand.getNumJokers() == 0) return matches(hand);
            return hand.getNumJokers() == 1 && HandTypes.TWO_PAIR.matches(hand);
        }
    },
    THREE_OF_A_KIND {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(3);
        }
        @Override
        public boolean matchesJoker (Hand hand) {
            if (hand.getNumJokers() == 0) return matches(hand);
            return hand.getNumJokers() > 1
                    || hand.getNumJokers() == 1 && hand.getCardFrequencies().containsValue(2);
        }
    },
    TWO_PAIR {
        @Override
        public boolean matches(Hand hand) {
            int pairCount = 0;
            for (int i : hand.getCardFrequencies().values()) {
                if (i == 2) {
                    pairCount++;
                }
            }
            return pairCount == 2;
        }
        @Override
        public boolean matchesJoker (Hand hand) {
            if (hand.getNumJokers() == 0) return matches(hand);
            return false; /* You would never upgrade a hand to TWO_PAIR if you had a Joker.
            *   If you had a Joker and "AAQK", you would do THREE_OF_A_KIND.
            *   If you had two Jokers and "AAQ", you would do FOUR_OF_A_KIND. */
        }
    },
    ONE_PAIR {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(2);
        }
        @Override
        public boolean matchesJoker (Hand hand) {
            if (hand.getNumJokers() == 0) return matches(hand);
            return true;    // This is the worst Hand you can have if you have a Joker.
        }
    },
    HIGH_CARD {
        @Override
        public boolean matches(Hand hand) {
            // This HandType matches all Hands no matter what.
            return true;
        }
        @Override
        public boolean matchesJoker (Hand hand) {
            return true;
        }
    };

    public abstract boolean matches(Hand hand);
    public abstract boolean matchesJoker(Hand hand);
}
