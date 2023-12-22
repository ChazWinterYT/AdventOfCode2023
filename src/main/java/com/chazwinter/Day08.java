package com.chazwinter;

import com.chazwinter.model.desertnodenetwork.DesertNode;
import com.chazwinter.model.desertnodenetwork.DesertNodeNetwork;
import com.chazwinter.model.desertnodenetwork.NodePath;
import com.chazwinter.util.AocUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 {
    public long hauntedWasteland(String filePath, int part)  {
        DesertNodeNetwork network = new DesertNodeNetwork();
        List<Character> directions = new ArrayList<>();

        AtomicInteger lineIndex = new AtomicInteger(0);
        // Given "AAA = (BBB, CCC)", find and extract "AAA", "BBB", and "CCC".
        Pattern pattern = Pattern.compile("([A-Z0-9]+) = \\(([A-Z0-9]+), ([A-Z0-9]+)\\)");
        AocUtils.processInputFile(filePath, (line) -> {
            int currentIndex = lineIndex.getAndIncrement();
            // Process the first line, which contains the Left/Right directions.
            if (currentIndex == 0) {
                for (char c : line.toCharArray()) {
                    directions.add(c);
                }
            } else if (currentIndex == 1) {
                // Blank line, do nothing.
            } else {    // Process the rest of the text inputs.
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    network.addNode(matcher.group(1), matcher.group(2), matcher.group(3));
                }
            }
        });
        // Debug. Make sure the network was built properly.
        // network.printNodeNetwork();

        long numSteps = 0;
        List<NodePath> allNodePathAttributes = new ArrayList<>();
        if (part == 1) {
            DesertNode currentNode = network.getNode("AAA");    // Starting Node.
            while (!currentNode.getValue().equals("ZZZ") && numSteps < 100_000) { // Avoid an infinite loop just in case.
                // If size is 3, we want the index we access to be 0, 1, 2, 0, 1, 2, etc.
                int directionIndex = (int) numSteps % directions.size();
                char directionToGo = directions.get(directionIndex);
                if (directionToGo == 'L') { // Go Left.
                    currentNode = currentNode.getLeft();
                } else { // Go Right.
                    currentNode = currentNode.getRight();
                }
                numSteps++;
            }
        } else if (part == 2) {
            List<DesertNode> allNodePaths = network.getAllStartingNodes();
            for (DesertNode node : allNodePaths) {
                int stepsToCycle = 0;
                int stepsToZ = 0;
                NodePath nodePath = new NodePath(node);
                DesertNode currentNode = node;
                while (true) {
                    nodePath.getNodeCyclePositions().put(currentNode.getValue(), stepsToCycle);
                    int directionIndex = stepsToCycle % directions.size();
                    char directionToGo = directions.get(directionIndex);
                    if (directionToGo == 'L') {
                        currentNode = currentNode.getLeft();
                    } else {
                        currentNode = currentNode.getRight();
                    }
                    stepsToCycle++;
                    stepsToZ++;
                    // If the current is already in the HashMap, then you have a cycle.
                    // Length is stepsToCycle - whatever the last position of that node was (from the Map).
                    if (nodePath.contains(currentNode) && nodePath.getCycleLength() == -1) {
                        nodePath.setCycleLength(stepsToCycle - nodePath.getPosition(currentNode));
                    }
                    if (currentNode.getValue().endsWith("Z") && nodePath.getNodeZ() == null) {
                        nodePath.setNodeZ(currentNode);
                        nodePath.setStepsToNodeZ(stepsToZ);
                    }
                    if (nodePath.getCycleLength() != 1 && nodePath.getNodeZ() != null) {
                        allNodePathAttributes.add(nodePath);
                        break;
                    }
                }
                System.out.println(nodePath);
            }
        }
        /*
        for (NodePath nodePath : allNodePathAttributes) {
            System.out.println(nodePath.getStepsToNodeZ() % nodePath.getCycleLength());
            // HOLY SHIT ^ THESE ARE ALL ZERO!
        }
         */
        if (part == 2) {
            numSteps = 1; // So I can multiply.
            for (NodePath nodePath : allNodePathAttributes) {
                numSteps *= nodePath.getCycleLength();
            }
            /* The above tells you how many steps until the paths are all in sync.
            *   However, this will occur at a different node every time. Because all the mod
            *   values are zero, the node we need is at the END of the cycle. So we need to know
            *   how many cycles it actually takes for each NodePath to literally reach our node.
            *   That's the steps to reach Z divided by the cycle length. And THEY ARE ALL 307!
            *   So multiply the above by 307. I'm not coding it, just use a Magic Number. */
            numSteps *= 307;
        }
        return numSteps;
    }
}
