package com.chazwinter.model.pipemaze;

import com.chazwinter.util.AocUtils;

public class PipeNodeProcessor {
    public static void addNeighborsIfValid(PipeNode currentNode, PipeNodeNetwork network) {
        if (currentNode.getNodeType() == NodeType.START) return;    // We already processed the Start node.
        /* I'm gonna process each NodeType's rules individually. I can't think of a cleaner way to do it. */
        switch(currentNode.getNodeType()) {
            case HORIZONTAL_PIPE:
                processHorizontalPipe(currentNode, network);
                break;
            case VERTICAL_PIPE:
                processVerticalPipe(currentNode, network);
                break;
            case SEVEN_BEND:
                processSevenBend(currentNode, network);
                break;
            case L_BEND:
                processLBend(currentNode, network);
                break;
            case J_BEND:
                processJBend(currentNode, network);
                break;
            case F_BEND:
                processFBend(currentNode, network);
                break;
        }
    }

    private static void processHorizontalPipe(PipeNode currentNode, PipeNodeNetwork network) {
        // Check nodes to the West and East.
        PipeNode westNode = null, eastNode = null;
        if (AocUtils.isInBounds(currentNode.getRow(), currentNode.getCol() - 1, network.getNetworkAsList())) {
            westNode = network.get(currentNode.getRow(), currentNode.getCol() - 1);
            if (validWestNode(westNode)) {
                currentNode.addNeighbor(westNode);
            }
        }
        if (AocUtils.isInBounds(currentNode.getRow(), currentNode.getCol() + 1, network.getNetworkAsList())) {
            eastNode = network.get(currentNode.getRow(), currentNode.getCol() + 1);
            if (validEastNode(eastNode)) {
                currentNode.addNeighbor(eastNode);
            }
        }
    }

    private static void processVerticalPipe(PipeNode currentNode, PipeNodeNetwork network) {
        // Check nodes to the North and South.
        PipeNode northNode = null, southNode = null;
        if (AocUtils.isInBounds(currentNode.getRow() - 1, currentNode.getCol(), network.getNetworkAsList())) {
            northNode = network.get(currentNode.getRow() - 1, currentNode.getCol());
            if (validNorthNode(northNode)) {
                currentNode.addNeighbor(northNode);
            }
        }
        if (AocUtils.isInBounds(currentNode.getRow() + 1, currentNode.getCol(), network.getNetworkAsList())) {
            southNode = network.get(currentNode.getRow() + 1, currentNode.getCol());
            if (validSouthNode(southNode)) {
                currentNode.addNeighbor(southNode);
            }
        }
    }

    private static void processSevenBend(PipeNode currentNode, PipeNodeNetwork network) {
        // Check nodes to the South and West.
        PipeNode southNode = null, westNode = null;
        if (AocUtils.isInBounds(currentNode.getRow() + 1, currentNode.getCol(), network.getNetworkAsList())) {
            southNode = network.get(currentNode.getRow() + 1, currentNode.getCol());
            if (validSouthNode(southNode)) {
                currentNode.addNeighbor(southNode);
            }
        }
        if (AocUtils.isInBounds(currentNode.getRow(), currentNode.getCol() - 1, network.getNetworkAsList())) {
            westNode = network.get(currentNode.getRow(), currentNode.getCol() - 1);
            if (validWestNode(westNode)) {
                currentNode.addNeighbor(westNode);
            }
        }
    }

    private static void processLBend(PipeNode currentNode, PipeNodeNetwork network) {
        // Check nodes to the North and East.
        PipeNode northNode = null, eastNode = null;
        if (AocUtils.isInBounds(currentNode.getRow() - 1, currentNode.getCol(), network.getNetworkAsList())) {
            northNode = network.get(currentNode.getRow() - 1, currentNode.getCol());
            if (validNorthNode(northNode)) {
                currentNode.addNeighbor(northNode);
            }
        }
        if (AocUtils.isInBounds(currentNode.getRow(), currentNode.getCol() + 1, network.getNetworkAsList())) {
            eastNode = network.get(currentNode.getRow(), currentNode.getCol() + 1);
            if (validEastNode(eastNode)) {
                currentNode.addNeighbor(eastNode);
            }
        }
    }

    private static void processJBend(PipeNode currentNode, PipeNodeNetwork network) {
        // Check nodes to the North and West.
        PipeNode northNode = null, westNode = null;
        if (AocUtils.isInBounds(currentNode.getRow() - 1, currentNode.getCol(), network.getNetworkAsList())) {
            northNode = network.get(currentNode.getRow() - 1, currentNode.getCol());
            if (validNorthNode(northNode)) {
                currentNode.addNeighbor(northNode);
            }
        }
        if (AocUtils.isInBounds(currentNode.getRow(), currentNode.getCol() - 1, network.getNetworkAsList())) {
            westNode = network.get(currentNode.getRow(), currentNode.getCol() - 1);
            if (validWestNode(westNode)) {
                currentNode.addNeighbor(westNode);
            }
        }
    }

    private static void processFBend(PipeNode currentNode, PipeNodeNetwork network) {
        // Check nodes to the South and East.
        PipeNode southNode = null, eastNode = null;
        if (AocUtils.isInBounds(currentNode.getRow() + 1, currentNode.getCol(), network.getNetworkAsList())) {
            southNode = network.get(currentNode.getRow() + 1, currentNode.getCol());
            if (validSouthNode(southNode)) {
                currentNode.addNeighbor(southNode);
            }
        }
        if (AocUtils.isInBounds(currentNode.getRow(), currentNode.getCol() + 1, network.getNetworkAsList())) {
            eastNode = network.get(currentNode.getRow(), currentNode.getCol() + 1);
            if (validEastNode(eastNode)) {
                currentNode.addNeighbor(eastNode);
            }
        }
    }

    private static boolean validNorthNode(PipeNode northNode) {
        // Valid connections from the North: |, 7, F.
        return northNode.getNodeType() == NodeType.VERTICAL_PIPE
                || northNode.getNodeType() == NodeType.SEVEN_BEND
                || northNode.getNodeType() == NodeType.F_BEND;

    }

    private static boolean validSouthNode(PipeNode southNode) {
        // Valid connections from the South: |, J, L.
        return southNode.getNodeType() == NodeType.VERTICAL_PIPE
                || southNode.getNodeType() == NodeType.J_BEND
                || southNode.getNodeType() == NodeType.L_BEND;
    }

    private static boolean validWestNode(PipeNode westNode) {
        // Valid connections from the West: -, L, F.
        return westNode.getNodeType() == NodeType.HORIZONTAL_PIPE
                || westNode.getNodeType() == NodeType.L_BEND
                || westNode.getNodeType() == NodeType.F_BEND;
    }

    private static boolean validEastNode(PipeNode eastNode) {
        // Valid connections from the East: -, 7, J
        return eastNode.getNodeType() == NodeType.HORIZONTAL_PIPE
                || eastNode.getNodeType() == NodeType.SEVEN_BEND
                || eastNode.getNodeType() == NodeType.J_BEND;
    }
}
