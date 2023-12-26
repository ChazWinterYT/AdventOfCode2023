package com.chazwinter.model.pipemaze;

import java.util.ArrayList;
import java.util.List;

public class PipeNode {
    char nodeType;
    int row;
    int col;
    int level;
    List<PipeNode> neighbors;
    boolean visited;

    public PipeNode(char nodeType, int row, int col) {
        this.nodeType = nodeType;
        this.row = row;
        this.col = col;
        neighbors = new ArrayList<>();
        visited = false;
    }

    public char getNodeType() {
        return nodeType;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addNeighbor(PipeNode neighbor) {
        this.neighbors.add(neighbor);
    }

    public List<PipeNode> getNeighbors() {
        return neighbors;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return String.format("nodeType: %c. row: %d col: %d.",
                nodeType, row, col);
    }
}
