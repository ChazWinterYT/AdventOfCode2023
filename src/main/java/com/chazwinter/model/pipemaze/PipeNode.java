package com.chazwinter.model.pipemaze;

import java.util.ArrayList;
import java.util.List;

public class PipeNode {
    NodeType nodeType;
    int row;
    int col;
    int level;
    List<PipeNode> neighbors;
    boolean visited;

    public PipeNode(char nodeType, int row, int col) {
        this.nodeType = NodeType.fromSymbol(nodeType);
        this.row = row;
        this.col = col;
        neighbors = new ArrayList<>();
        visited = false;
    }

    public NodeType getNodeType() {
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

    /**
     * Add a node as a neighbor for the current node.
     * @param neighbor Node that should be linked as a neighbor.
     */
    public void addNeighbor(PipeNode neighbor) {
        this.neighbors.add(neighbor);
    }

    /**
     * Same as above, but accepts a List of neighbors instead of a single neighbor.
     * @param neighbors The List of neighbors that should be linked to the current node.
     */
    public void addListOfNeighbors(List<PipeNode> neighbors) {
        for (PipeNode neighbor : neighbors) {
            addNeighbor(neighbor);
        }
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
        return String.format("nodeType: %s. row: %d col: %d.",
                nodeType, row, col);
    }
}
