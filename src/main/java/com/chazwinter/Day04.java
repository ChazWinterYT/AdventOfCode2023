package com.chazwinter;

import com.chazwinter.model.ScratchCard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* NOTE: This class uses the model.ScratchCard class. */
public class Day04 {
    /* Compile a RegEx pattern for extracting numbers. */
    public static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    /* Retain a Map of Scratch Cards for Part 2. */
    Map<Integer, ScratchCard> scratchCards = new HashMap<>();

    public int scratchCards(String filePath, int part) throws IOException {
        int totalCardScore = 0, totalScratchCards = 0;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            // Split by colon : to get the cardNumber.
            String[] splitByColon = line.split(":");
            int cardNumber = extractNumberFromString(splitByColon[0]);
            ScratchCard card = new ScratchCard(cardNumber);
            // Split by pipe | to get the numbers on the card, and store them as a Set.
            String[] splitByPipe = splitByColon[1].split("\\|");
            Set<Integer> winningNumbers = extractSetOfNumbersFromString(splitByPipe[0]);
            card.setWinningNumbers(winningNumbers);
            Set<Integer> yourNumbers = extractSetOfNumbersFromString(splitByPipe[1]);
            card.setYourNumbers(yourNumbers);
            // Calculate the class fields for this card. Actually needed for both parts.
            this.generateCardData(card);
            // We need to store all the Scratch Cards for Part 2.
            if (part == 2) scratchCards.put(card.getCardNumber(), card);
            // No need to store the Scratch Cards for Part 1, so just perform the calculation here.
            if (part == 1) totalCardScore += card.getScore();

            // System.out.println(card);    // Debug. See if the ScratchCard was properly generated.
        }
        /* We need the total number of Scratch Cards. By the time we encounter a card,
             we already know exactly how many copies it should have, so just add it to the total.
             Then make copies of the subsequent cards based on the current card's data. */
        if (part == 2) {
            for (int i = 1; i <= scratchCards.size(); i++) {
                ScratchCard currentCard = scratchCards.get(i);
                int copiesToMake = currentCard.getCopies();
                totalScratchCards += copiesToMake;
                makeCopiesOfScratchCards(currentCard);
            }
        }
        return part == 1 ? totalCardScore : totalScratchCards;
    }

    /**
     * Make copies of future scratch cards based on how many winning numbers are on the current one.
     * Ex: If Card 1 has 4 winning numbers, then make a copy of cards 2, 3, 4, and 5 (four cards).
     * If there were multiple "Card 1s" to begin with, then all of them make additional copies.
     * Ex: There are six Card 3s, and Card 3 has 2 winning numbers. Make SIX copies of cards 4 and 5 (two cards).
     * @param currentCard The card used to determine the number of copies of the subsequent cards.
     */
    private void makeCopiesOfScratchCards(ScratchCard currentCard) {
        int cardNumber = currentCard.getCardNumber();
        int numCardsToCopy = currentCard.getWinningNumCount();
        int copiesToMake = currentCard.getCopies();
        for (int i = cardNumber + 1; i <= cardNumber + numCardsToCopy; i++) {
            ScratchCard cardToCopy = scratchCards.get(i);
            int currentCopies = cardToCopy.getCopies();
            cardToCopy.setCopies(currentCopies + copiesToMake);
        }
    }

    /**
     * Helper method for pulling the number out of a String.
     * @param str The string to check (ex: "Card 123").
     * @return The number that was found (ex: 123).
     */
    private int extractNumberFromString(String str) {
        Matcher matcher = NUMBER_PATTERN.matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        throw new IllegalArgumentException("No number in that String.");
    }

    /**
     * Helper method to extract a Set of numbers from a String. I used a Stream! Alyson would be proud.
     * @param strToSplit The string containing the numbers we need.
     * @return A Set containing those numbers.
     */
    private Set<Integer> extractSetOfNumbersFromString(String strToSplit) {
        String[] splitNumbers = strToSplit.split(" ");
        return Arrays.stream(splitNumbers)
                .filter(str -> !str.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    /**
     * Generate data for this Scratch Card, such as score, number of winning numbers, etc.
     * @param card The card for which to generate data.
     */
    private void generateCardData(ScratchCard card) {
        Set<Integer> intersection = new HashSet<>(card.getWinningNumbers());
        intersection.retainAll(card.getYourNumbers());
        card.setWinningNumCount(intersection.size());
        card.setScore((int)Math.pow(2, intersection.size() - 1));
    }
}
