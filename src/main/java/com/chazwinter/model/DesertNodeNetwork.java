package com.chazwinter.model;

import java.util.HashMap;
import java.util.Map;

public class DesertNodeNetwork {
    private Map<String, DesertNode> desertNodeMap;

    public DesertNodeNetwork() {
        this.desertNodeMap = new HashMap<>();
    }

    public void printNodeNetwork() {
        for (String value : desertNodeMap.keySet()) {
            System.out.println(desertNodeMap.get(value));
        }
    }

    public DesertNode getOrCreateNode(String nodeName) {
        if (desertNodeMap.containsKey(nodeName)) {
            return desertNodeMap.get(nodeName);
        }
        return desertNodeMap.put(nodeName, new DesertNode(nodeName));
    }

    public void addNode(String nodeName, String leftNodeName, String rightNodeName) {
        DesertNode node = getOrCreateNode(nodeName);
        node.setLeft(getOrCreateNode(leftNodeName));
        node.setRight(getOrCreateNode(rightNodeName));
    }
}
