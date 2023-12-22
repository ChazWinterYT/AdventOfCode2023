package com.chazwinter.model.desertnodenetwork;

import com.chazwinter.model.desertnodenetwork.DesertNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesertNodeNetwork {
    private Map<String, DesertNode> desertNodeMap;

    public DesertNodeNetwork() {
        this.desertNodeMap = new HashMap<>();
    }

    /**
     * Prints every DesertNode in the network, one per line. For debugging.
     */
    public void printNodeNetwork() {
        for (String value : desertNodeMap.keySet()) {
            System.out.println(desertNodeMap.get(value));
        }
    }

    /**
     * Gets the requested node in the network.
     * @param nodeName The name of the node to retrieve.
     * @return the requested node.
     */
    public DesertNode getNode(String nodeName) {
        return desertNodeMap.get(nodeName);
    }

    /**
     * Add a node to the network, complete with its left and right paths.
     * @param nodeName The name of the parent node to add to the network.
     * @param leftNodeName The name of the parent node's left child.
     * @param rightNodeName The name of the parent node's right child.
     */
    public void addNode(String nodeName, String leftNodeName, String rightNodeName) {
        DesertNode node = getOrCreateNode(nodeName);
        node.setLeft(getOrCreateNode(leftNodeName));
        node.setRight(getOrCreateNode(rightNodeName));
    }

    /**
     * Gets a node in the network if it already exists. Otherwise, it creates the node.
     * @param nodeName The name of the node to retrieve or create.
     * @return the requested node.
     */
    public DesertNode getOrCreateNode(String nodeName) {
        if (desertNodeMap.containsKey(nodeName)) {
            return desertNodeMap.get(nodeName);
        }
        DesertNode node = new DesertNode(nodeName);
        desertNodeMap.put(nodeName, node);
        return node;
    }

    public List<DesertNode> getAllStartingNodes() {
        List<DesertNode> startingNodes = new ArrayList<>();
        for (String nodeName : desertNodeMap.keySet()) {
            if (nodeName.endsWith("A")) {
                startingNodes.add(desertNodeMap.get(nodeName));
            }
        }
        return startingNodes;
    }
}
