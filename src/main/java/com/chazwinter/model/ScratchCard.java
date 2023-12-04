package com.chazwinter.model;

import java.util.Set;

/* NOTE: This class is needed for Day04. */
public class ScratchCard {
    private int cardNumber;
    private Set<Integer> winningNumbers;
    private Set<Integer> yourNumbers;
    private int score;
    private int winningNumCount;
    private int copies;

    public ScratchCard(int cardNumber) {
        this.cardNumber = cardNumber;
        this.copies = 1;
    }

    public void addWinningNumber(int numToAdd) {
        this.winningNumbers.add(numToAdd);
    }

    public void addYourNumber(int yourNumber) {
        this.yourNumbers.add(yourNumber);
    }

    public Set<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public void setWinningNumbers(Set<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    public Set<Integer> getYourNumbers() {
        return yourNumbers;
    }

    public void setYourNumbers(Set<Integer> yourNumbers) {
        this.yourNumbers = yourNumbers;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWinningNumCount() {
        return winningNumCount;
    }

    public void setWinningNumCount(int winningNumCount) {
        this.winningNumCount = winningNumCount;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    @Override
    public String toString() {
        return String.format(
            "Card %d (Score %d, Count %d, Copies %d): w:[%s] | y:[%s]",
                cardNumber, score, winningNumCount, copies, winningNumbers, yourNumbers);
    }
}
