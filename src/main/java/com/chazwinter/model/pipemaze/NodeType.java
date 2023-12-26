package com.chazwinter.model.pipemaze;

public enum NodeType {
    VERTICAL_PIPE('|', new int[][]{{0, -1}, {0, 1}}),       // North and South
    HORIZONTAL_PIPE('-', new int[][]{{-1, 0}, {1, 0}}),     // East and West
    L_BEND('L', new int[][]{{0, -1}, {1, 0}}),      // North and East
    J_BEND('J', new int[][]{{0, -1}, {-1, 0}}),     // North and West
    SEVEN_BEND('7', new int[][]{{0, 1}, {-1, 0}}),  // South and West
    F_BEND('F', new int[][]{{0, 1}, {1, 0}}),       // South and East
    GROUND('.', new int[][]{}),     // No movement
    START('S', new int[][]{});      // Starting position, direction unknown

    private final char symbol;
    private final int[][] directions;  // Array of {x, y} pairs

    NodeType(char symbol, int[][] directions) {
        this.symbol = symbol;
        this.directions = directions;
    }

    public char getSymbol() {
        return symbol;
    }

    public int[][] getDirections() {
        return directions;
    }

    public static NodeType fromSymbol(char symbol) {
        for (NodeType type : NodeType.values()) {
            if (type.getSymbol() == symbol) {
                return type;
            }
        }
        throw new IllegalArgumentException("No NodeType with symbol: " + symbol);
    }
}

