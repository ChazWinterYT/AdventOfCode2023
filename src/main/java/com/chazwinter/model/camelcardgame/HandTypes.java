package com.chazwinter.model.camelcardgame;

public enum HandTypes {
    FIVE_OF_A_KIND {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(5);
        }
    },
    FOUR_OF_A_KIND {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(4);
        }
    },
    FULL_HOUSE {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(3)
                    && hand.getCardFrequencies().containsValue(2);
        }
    },
    THREE_OF_A_KIND {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(3);
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
    },
    ONE_PAIR {
        @Override
        public boolean matches(Hand hand) {
            return hand.getCardFrequencies().containsValue(2);
        }
    },
    HIGH_CARD {
        @Override
        public boolean matches(Hand hand) {
            // This HandType matches all Hands no matter what.
            return true;
        }
    };

    public abstract boolean matches(Hand hand);
}
