package com.chazwinter;

import com.chazwinter.util.AocUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day03 {
    public static final int PUZZLE_SIZE = 140;   // 10 for test input, 140 for full input
    char[][] engineGrid = new char[PUZZLE_SIZE][PUZZLE_SIZE];
    /* Two arrays to represent where a neighbor cell is relative to the current one.
        For example, the top-left cell is row-1 and col-1. */
    int[] dRow = new int[] {-1, -1, -1,
                             0,      0,
                             1,  1,  1};
    int[] dCol = new int[] {-1,  0,  1,
                            -1,      1,
                            -1,  0,  1};

    public int machineParts(String filePath, int part) {
        // Store puzzle input as a 2D char array (DID YOU SET THE PUZZLE SIZE?).
        AtomicInteger lineNumber = new AtomicInteger(0);

        AocUtils.processInputFile(filePath, (line) -> {
            for (int i = 0; i < line.length(); i++) {
                engineGrid[lineNumber.get()][i] = line.charAt(i);
            }
            lineNumber.getAndAdd(1);
        });

        if (part == 1) {
            // If we see a digit, check if it has symbols around it.
            int sumOfEngineParts = 0;
            for (int i = 0; i < engineGrid.length; i++) {
                for (int j = 0; j < engineGrid[0].length; j++) {
                    if (!Character.isDigit(engineGrid[i][j])) {
                        continue;
                    }
                    sumOfEngineParts += searchForSymbols(i, j);
                }
            }
            return sumOfEngineParts;
        }
        if (part == 2) {
            int sumOfGearProducts = 0;
            /* If we see a gear, check if it has two numbers around it.
            * We can't destroy the numbers this time, so copy the surrounding rows
            * into a new 2D array, so we can destroy THOSE numbers instead. */
            for (int i = 0; i < engineGrid.length; i++) {
                for (int j = 0; j < engineGrid[0].length; j++) {
                    if (engineGrid[i][j] == '*') {
                        char[][] rowsAroundGear = getRowsAroundGear(i, j);
                        List<Integer> numsAroundGear = searchForNumbers(rowsAroundGear, 1, j);
                        // System.out.println(numsAroundGear);
                        if (numsAroundGear.size() == 2) {
                            int product = numsAroundGear.get(0) * numsAroundGear.get(1);
                            sumOfGearProducts += product;
                        }
                    }
                }
            }
            return sumOfGearProducts;
        }
        return -1;
    }

    /**
     * Search the grid for symbols (not a digit and not a period).
     * Only works for part 1. searchForDigits() is the part 2 equivalent.
     * @return The number near that symbol, if there is one.
     */
    private int searchForSymbols(int row, int col) {
        int valueToAdd = 0;
        for (int i = 0; i < dRow.length; i++) {
            int newRow = row + dRow[i];
            int newCol = col + dCol[i];
            if (isInBounds(newRow, newCol) && isASymbol(newRow, newCol)) {
                // System.out.printf("Symbol %c found near value %c (row %d, col %d)\n",
                //        engineGrid[newRow][newCol], engineGrid[row][col], row, col);
                valueToAdd = getValueFromGrid(engineGrid[row], row, col);
            }
        }
        return valueToAdd;
    }

    /**
     * Helper method to determine if a cell is in bounds before taking action on it.
     * @param row The row of the cell to check.
     * @param col the column of the cell the check.
     * @return true if the cell is in bounds, false if it is not.
     */
    private boolean isInBounds(int row, int col) {
        // DID YOU SET THE PUZZLE SIZE?
        return row >= 0 && row < PUZZLE_SIZE
                && col >= 0 && col < PUZZLE_SIZE;
    }

    /**
     * Helper method to determine if a character in a cell is a symbol (not a digit or a period).
     */
    private boolean isASymbol(int row, int col) {
        char symbol = engineGrid[row][col];
        return !Character.isDigit(symbol) && symbol != '.';
    }

    /**
     * When a digit is found in the grid, get the full number associated with that digit.
     * This only works for Part 1; This method is overloaded later on for Part 2.
     * @param rowChars The array where the digit was found.
     * @param row The row of that array in the overall grid.
     * @param col The column where the digit was found.
     * @return The number associated with the found digit.
     */
    private int getValueFromGrid(char[] rowChars, int row, int col) {
        int result = 0;
        StringBuilder sb = new StringBuilder();
        // Add the current digit (also destroy it, so we don't count it later on)
        sb.append(rowChars[col]);
        engineGrid[row][col] = '.';
        // Get digits to the left (and destroy them, so they don't trigger this same method later)
        int left = col - 1;
        while (isInBounds(row, left) && isADigit(row, left)) {
            sb.insert(0, rowChars[left]);
            engineGrid[row][left] = '.';
            left--;
        }
        // Get digits to the right (and destroy them, so they don't trigger this same method later)
        int right = col + 1;
        while (isInBounds(row, right) && isADigit(row, right)) {
            sb.append(rowChars[right]);
            engineGrid[row][right] = '.';
            right++;
        }
        return Integer.parseInt(sb.toString());
    }

    /**
     * Helper method to determine if a character in a cell is a digit.
     */
    private boolean isADigit(int row, int col) {
        char symbol = engineGrid[row][col];
        return Character.isDigit(symbol);
    }

    /**
     * For Part 2. When a gear is found, we need a COPY of the three grid rows surrounding that gear.
     * If the gear is in the top or bottom row, TOO BAD! Three rows are REQUIRED.
     * So just make up an extra row full of dots!
     * @param i The row where the gear was found in the overall grid.
     * @param j The column where the gear was found in that row.
     * @return The 3 x [PUZZLE_SIZE] array surrounding the found gear.
     */
    private char[][] getRowsAroundGear(int i, int j) {
        char[][] rowsAroundGear = new char[3][PUZZLE_SIZE];
        // Row above the gear, if it exists.
        if (isInBounds(i - 1, 0)) {
            rowsAroundGear[0] = copyArray(engineGrid[i - 1]);
        } else {    // The row didn't exist. Just make up a new top row.
            Arrays.fill(rowsAroundGear[0], '.');
        }
        // Row that the gear is in. This better exist lol.
        rowsAroundGear[1] = copyArray(engineGrid[i]);
        // Row below the gear, if it exists.
        if (isInBounds(i + 1, 0)) {
            rowsAroundGear[2] = copyArray(engineGrid[i + 1]);
        } else {    // The row didn't exist. Just make up a new bottom row.
            Arrays.fill(rowsAroundGear[2], '.');
        }
        removeAllOtherGears(rowsAroundGear, j);
        return rowsAroundGear;
    }

    /**
     * Helper method to produce a copy of an array.
     */
    private char[] copyArray(char[] rowToCopy) {
        return Arrays.copyOf(rowToCopy, PUZZLE_SIZE);
    }

    /**
     * When producing a copy of the array, we only want it to include the CURRENT gear,
     * not any other gears that might coincidentally exist within these three rows.
     * @param rowsAroundGear The copied 2D array containing our gear.
     * @param ourGear The gear we want to keep.
     */
    private void removeAllOtherGears(char[][] rowsAroundGear, int ourGear) {
        for (int i = 0; i < rowsAroundGear.length; i++) {
            for (int j = 0; j < PUZZLE_SIZE; j++) {
                if (i == 1 && j == ourGear) continue;    // Don't remove our gear!
                if (rowsAroundGear[i][j] == '*') {
                    rowsAroundGear[i][j] = '.';
                }
            }
        }
    }

    /**
     * When a gear is found, we need to find the numbers surrounding it, and
     * add them to a List to evaluate later.
     * @param rowsAroundGear The copy of the grid rows surrounding the gear.
     * @param row The row where the gear is located.
     *            Due to the copied grid's design, the gear is always in row 1. row = 1 is always passed in.
     * @param col The column where the gear is located.
     * @return A List of all numbers that surround the gear.
     */
    private List<Integer> searchForNumbers(char[][] rowsAroundGear, int row, int col) {
        // System.out.println(Arrays.deepToString(rowsAroundGear));
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < dRow.length; i++) {
            int newRow = row + dRow[i];
            int newCol = col + dCol[i];
            if (isInBounds(newRow, newCol) && Character.isDigit(rowsAroundGear[newRow][newCol])) {
                // System.out.printf("Digit %c (row %d, col %d) found near symbol %c\n",
                //        rowsAroundGear[newRow][newCol], newRow, newCol, rowsAroundGear[row][col]);
                numbers.add(getValueFromGrid(rowsAroundGear, rowsAroundGear[newRow], newRow, newCol));
            }
        }
        return numbers;
    }

    /**
     * The other getValueFromGrid() method only specifically works for part 1, and I don't want to
     * refactor it, so here is an overloaded version that specifically works for part 2.
     */
    private int getValueFromGrid(char[][] rowsAroundGear, char[] rowChars, int row, int col) {
        int result = 0;
        StringBuilder sb = new StringBuilder();
        // Add the current digit (also destroy it, so we don't count it later on)
        sb.append(rowChars[col]);
        rowsAroundGear[row][col] = '.';
        // Get digits to the left (and destroy them, so they don't trigger this same method later)
        int left = col - 1;
        while (isInBounds(row, left) && Character.isDigit(rowChars[left])) {
            sb.insert(0, rowChars[left]);
            rowsAroundGear[row][left] = '.';
            left--;
        }
        // Get digits to the right (and destroy them, so they don't trigger this same method later)
        int right = col + 1;
        while (isInBounds(row, right) && Character.isDigit(rowChars[right])) {
            sb.append(rowChars[right]);
            rowsAroundGear[row][right] = '.';
            right++;
        }
        return Integer.parseInt(sb.toString());
    }
}
