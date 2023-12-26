package com.chazwinter;

import com.chazwinter.model.pipemaze.NodeType;
import com.chazwinter.model.pipemaze.PipeNode;
import com.chazwinter.model.pipemaze.PipeNodeNetwork;
import com.chazwinter.model.pipemaze.PipeNodeProcessor;
import com.chazwinter.util.AocUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/* Note: This class uses classes from the model.pipemaze package. */
public class Day10 {
    public int pipeMaze(String filePath, int part) {
        PipeNodeNetwork network = new PipeNodeNetwork();
        /* rowNumber must be incremented with each line from the input file, so it must be concurrent. */
        AtomicInteger rowNumber = new AtomicInteger(0);
        /* Keep track of where the starting Node is, so we don't have to iterate twice. */
        int[] startingNodeLocation = new int[2];

        AocUtils.processInputFile(filePath, (line) -> {
            // Turn the current line of text into a List of PipeNodes.
            List<PipeNode> currentRowOfNodes = new ArrayList<>();
            for (int colNumber = 0; colNumber < line.length(); colNumber++) {
                PipeNode currentNode = new PipeNode(line.charAt(colNumber), rowNumber.get(), colNumber);
                /* Track the starting node once we find it. */
                if (currentNode.getNodeType() == NodeType.START) {
                    startingNodeLocation[0] = rowNumber.get();
                    startingNodeLocation[1] = colNumber;
                    currentNode.setLevel(0);
                    network.setRoot(currentNode);
                }
                /* Add each node to the network. */
                currentRowOfNodes.add(currentNode);
            }
            network.addListOfNodesToNetwork(currentRowOfNodes);
            rowNumber.getAndIncrement();
        });
        // Debug: Make sure the network was generated properly.
        // network.printNetwork();

        /* Starting Node does not have a shape, so we need to figure out what its neighbors are. */
        figureOutStartingNodeNeighbors(network);

        /* Perform BFS to find node farthest away from the start. */
        Queue<PipeNode> queue = new LinkedList<>();
        int maxStepsFromStart = 0;
        queue.add(network.getRoot());

        while (!queue.isEmpty()) {
            PipeNode currentNode = queue.poll();
            PipeNodeProcessor.addNeighborsIfValid(currentNode, network);
            for (PipeNode neighbor : currentNode.getNeighbors()) {
                if (!neighbor.isVisited()) {
                    neighbor.setVisited(true);
                    neighbor.setLevel(currentNode.getLevel() + 1);
                    maxStepsFromStart = Math.max(maxStepsFromStart, neighbor.getLevel());
                    queue.add(neighbor);
                }
            }
        }

        return maxStepsFromStart;
    }

    /**
     * The starting node does not specify its type, so we don't know what its neighbors are. This method checks all
     * four directions to see which nodes are the starting node's neighbors, and sets the correct ones as neighbors.
     * The nodes are checked by brute-force. There's probably a better way.
     * @param network The node network we're checking.
     */
    private void figureOutStartingNodeNeighbors(PipeNodeNetwork network) {
        PipeNode startingNode = network.getRoot();
        /* For the four directions around root, determine which two can point back to the start. */
        PipeNode nodeUp = null;
        if (AocUtils.isInBounds(startingNode.getRow() - 1, startingNode.getCol(), network.getNetworkAsList())) {
            nodeUp = network.get(startingNode.getRow() - 1, startingNode.getCol());
        }
        // nodeUp needs a pipe facing South. That's Vertical, 7, or F.
        if (nodeUp != null
                && (nodeUp.getNodeType() == NodeType.VERTICAL_PIPE
                || nodeUp.getNodeType() == NodeType.SEVEN_BEND
                || nodeUp.getNodeType() == NodeType.F_BEND)) {
            startingNode.addNeighbor(nodeUp);
        }

        PipeNode nodeDown = null;
        if (AocUtils.isInBounds(startingNode.getRow() + 1, startingNode.getCol(), network.getNetworkAsList())) {
            nodeDown = network.get(startingNode.getRow() + 1, startingNode.getCol());
        }
        // nodeDown needs a pipe facing North. That's Vertical, L, or J.
        if (nodeDown != null
                && (nodeDown.getNodeType() == NodeType.VERTICAL_PIPE
                || nodeDown.getNodeType() == NodeType.L_BEND
                || nodeDown.getNodeType() == NodeType.J_BEND)) {
            startingNode.addNeighbor(nodeDown);
        }

        PipeNode nodeLeft = null;
        if (AocUtils.isInBounds(startingNode.getRow(), startingNode.getCol() - 1, network.getNetworkAsList())) {
            nodeLeft = network.get(startingNode.getRow(), startingNode.getCol() - 1);
        }
        // nodeLeft needs a pipe facing East. That's Horizontal, L or F.
        if (nodeLeft != null
                && (nodeLeft.getNodeType() == NodeType.HORIZONTAL_PIPE
                || nodeLeft.getNodeType() == NodeType.L_BEND
                || nodeLeft.getNodeType() == NodeType.F_BEND)) {
            startingNode.addNeighbor(nodeLeft);
        }

        PipeNode nodeRight = null;
        if (AocUtils.isInBounds(startingNode.getRow(), startingNode.getCol() + 1, network.getNetworkAsList())) {
            nodeRight = network.get(startingNode.getRow(), startingNode.getCol() + 1);
        }
        // nodeRight needs a pipe facing West. That's Horizontal, J, or 7.
        if (nodeRight != null
                && (nodeRight.getNodeType() == NodeType.HORIZONTAL_PIPE
                || nodeRight.getNodeType() == NodeType.J_BEND
                || nodeRight.getNodeType() == NodeType.SEVEN_BEND)) {
            startingNode.addNeighbor(nodeRight);
        }
    }
}
