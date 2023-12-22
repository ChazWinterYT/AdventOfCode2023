package com.chazwinter.model.camelcardgame;

public enum HandTypes {
    FIVE_OF_A_KIND {
        @Override
        public boolean matchesNoJokers(Hand hand) {
            return hand.getCardFrequencySet().contains(5);
        }
        @Override
        public boolean matches(Hand hand) {
            if (hand.getNumJokers() == 0) return matchesNoJokers(hand);
            return hand.getNumJokers() > 3
                    || hand.getNumJokers() == 3 && hand.getCardFrequencySet().contains(2)
                    || hand.getNumJokers() == 2 && hand.getCardFrequencySet().contains(3)
                    || hand.getNumJokers() == 1 && hand.getCardFrequencySet().contains(4);
        }
    },
    FOUR_OF_A_KIND {
        @Override
        public boolean matchesNoJokers(Hand hand) {
            return hand.getCardFrequencySet().contains(4);
        }
        @Override
        public boolean matches(Hand hand) {
            if (hand.getNumJokers() == 0) return matchesNoJokers(hand);
            return hand.getNumJokers() > 2
                    || hand.getNumJokers() == 2 && hand.getCardFrequencySet().contains(2)
                    || hand.getNumJokers() == 1 && hand.getCardFrequencySet().contains(3);
        }
    },
    FULL_HOUSE {
        @Override
        public boolean matchesNoJokers(Hand hand) {
            return hand.getCardFrequencySet().contains(3)
                    && hand.getCardFrequencySet().contains(2);
        }
        @Override
        public boolean matches(Hand hand) {
            if (hand.getNumJokers() == 0) return matchesNoJokers(hand);
            return hand.getNumJokers() == 1 && HandTypes.TWO_PAIR.matchesNoJokers(hand);
        }
    },
    THREE_OF_A_KIND {
        @Override
        public boolean matchesNoJokers(Hand hand) {
            return hand.getCardFrequencySet().contains(3);
        }
        @Override
        public boolean matches(Hand hand) {
            if (hand.getNumJokers() == 0) return matchesNoJokers(hand);
            return hand.getNumJokers() > 1
                    || hand.getNumJokers() == 1 && hand.getCardFrequencySet().contains(2);
        }
    },
    TWO_PAIR {
        @Override
        public boolean matchesNoJokers(Hand hand) {
            int pairCount = 0;
            for (int i : hand.getCardFrequencies().values()) {
                if (i == 2) {
                    pairCount++;
                }
            }
            return pairCount == 2;
        }
        @Override
        public boolean matches(Hand hand) {
            if (hand.getNumJokers() == 0) return matchesNoJokers(hand);
            return false; /* You would never upgrade a hand to TWO_PAIR if you had a Joker.
            *   If you had a Joker and "AAQK", you would do THREE_OF_A_KIND.
            *   If you had two Jokers and "AAQ", you would do FOUR_OF_A_KIND. */
        }
    },
    ONE_PAIR {
        @Override
        public boolean matchesNoJokers(Hand hand) {
            return hand.getCardFrequencySet().contains(2);
        }
        @Override
        public boolean matches(Hand hand) {
            if (hand.getNumJokers() == 0) return matchesNoJokers(hand);
            return true;    // This is the worst Hand you can have if you have a Joker.
        }
    },
    HIGH_CARD {
        @Override
        public boolean matchesNoJokers(Hand hand) {
            // This HandType matches all Hands no matter what.
            return true;
        }
        @Override
        public boolean matches(Hand hand) {
            return true;
        }
    };

    public abstract boolean matchesNoJokers(Hand hand);
    public abstract boolean matches(Hand hand);
}
