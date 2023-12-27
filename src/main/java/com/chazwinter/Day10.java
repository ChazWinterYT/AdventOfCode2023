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

        /* Part 1: Perform BFS to find node farthest away from the start.
        * You actually need to do this for Part 2 as well, but only to mark the pipes visited. */
        Queue<PipeNode> queue = new LinkedList<>();
        int maxStepsFromStart = 0;
        queue.add(network.getRoot());
        network.getRoot().setVisited(true);

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
        if (part == 1) return maxStepsFromStart;

        int countInnerCells = 0;
        for (int row = 0; row < network.getNetworkAsList().size(); row++) {
            for (int col = 0; col < network.getNetworkAsList().get(0).size(); col++) {
                if (pipesCrossedIsOdd(network, row, col)) {
                    //System.out.printf("Node at row %d, col %d is inside the loop.\n", row, col);
                    countInnerCells++;
                }
            }
        }
        return countInnerCells;
    }

    /**
     * Determine if a cell within the network is inside the closed loop or outside of it.
     * Method currently does not work.
     * @param network The PipeNodeNetwork we want to check within.
     * @param row The row of the checked cell.
     * @param col The column of the checked cell.
     * @return true if that cell is inside the loop, false otherwise.
     */
    private boolean pipesCrossedIsOdd(PipeNodeNetwork network, int row, int col) {
        PipeNode currentCell = network.get(row, col);
        int numCrossings = 0;
        /* If the current cell is PART of the loop, then it's obviously not inside the loop. */
        if (currentCell.isVisited()) return false;
        /* From the current cell, go left. If you cross an odd number of boundaries, you're inside the loop.
        * Horizontal pipes don't count when moving left/right. You're moving tangent to the loop, not crossing it. */
        for (int colToCheck = col; colToCheck >= 0; colToCheck--) {
            PipeNode cellToCheck = network.get(row, colToCheck);
            if (!cellToCheck.isVisited()) continue;
            if (cellToCheck.getNodeType() != NodeType.HORIZONTAL_PIPE) {
                numCrossings++;
            }
        }
        return numCrossings % 2 == 1;
    }

    /**
     * The starting node does not specify its type, so we don't know what its neighbors are. This method checks all
     * four directions to see which nodes are the starting node's neighbors, and sets the correct ones as neighbors.
     * @param network The node network we're checking.
     */
    private void figureOutStartingNodeNeighbors(PipeNodeNetwork network) {
        PipeNode startingNode = network.getRoot();

        /* For the four directions around root, determine which two can point back to the start. */
        PipeNode nodeUp = null;
        if (AocUtils.isInBounds(startingNode.getRow() - 1, startingNode.getCol(), network.getNetworkAsList())) {
            nodeUp = network.get(startingNode.getRow() - 1, startingNode.getCol());
            if (PipeNodeProcessor.validNorthNode(nodeUp)) {
                startingNode.addNeighbor(nodeUp);
            }
        }

        PipeNode nodeDown = null;
        if (AocUtils.isInBounds(startingNode.getRow() + 1, startingNode.getCol(), network.getNetworkAsList())) {
            nodeDown = network.get(startingNode.getRow() + 1, startingNode.getCol());
            if (PipeNodeProcessor.validSouthNode(nodeDown)) {
                startingNode.addNeighbor(nodeDown);
            }
        }

        PipeNode nodeLeft = null;
        if (AocUtils.isInBounds(startingNode.getRow(), startingNode.getCol() - 1, network.getNetworkAsList())) {
            nodeLeft = network.get(startingNode.getRow(), startingNode.getCol() - 1);
            if (PipeNodeProcessor.validWestNode(nodeLeft)) {
                startingNode.addNeighbor(nodeLeft);
            }
        }

        PipeNode nodeRight = null;
        if (AocUtils.isInBounds(startingNode.getRow(), startingNode.getCol() + 1, network.getNetworkAsList())) {
            nodeRight = network.get(startingNode.getRow(), startingNode.getCol() + 1);
            if (PipeNodeProcessor.validEastNode(nodeRight)) {
                startingNode.addNeighbor(nodeRight);
            }
        }
    }
}
