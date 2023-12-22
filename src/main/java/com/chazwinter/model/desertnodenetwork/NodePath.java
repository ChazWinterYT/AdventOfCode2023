package com.chazwinter.model.desertnodenetwork;

import java.util.HashMap;
import java.util.Map;

public class NodePath {
    private DesertNode startingNode;
    private int cycleLength;
    private int stepsToNodeZ;
    private DesertNode nodeZ;
    private Map<String, Integer> nodeCyclePositions;

    public NodePath(DesertNode startingNode) {
        this.startingNode = startingNode;
        nodeCyclePositions = new HashMap<>();
        this.cycleLength = -1;  // Cycle length has not been determined yet.
    }

    public boolean contains(DesertNode node) {
        return nodeCyclePositions.containsKey(node.getValue());
    }

    public int getPosition(DesertNode node) {
        return nodeCyclePositions.get(node.getValue());
    }

    public DesertNode getStartingNode() {
        return startingNode;
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(int cycleLength) {
        this.cycleLength = cycleLength;
    }

    public int getStepsToNodeZ() {
        return stepsToNodeZ;
    }

    public void setStepsToNodeZ(int stepsToNodeZ) {
        this.stepsToNodeZ = stepsToNodeZ;
    }

    public DesertNode getNodeZ() {
        return nodeZ;
    }

    public void setNodeZ(DesertNode nodeZ) {
        this.nodeZ = nodeZ;
    }

    public Map<String, Integer> getNodeCyclePositions() {
        return nodeCyclePositions;
    }

    @Override
    public String toString() {
        return String.format("Node %s. Cycle in %d steps. Z found in %d steps.",
                startingNode.getValue(), cycleLength, stepsToNodeZ);
    }
}
