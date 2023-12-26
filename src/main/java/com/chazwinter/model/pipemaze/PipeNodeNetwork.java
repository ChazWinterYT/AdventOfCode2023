package com.chazwinter.model.pipemaze;

import java.util.ArrayList;
import java.util.List;

public class PipeNodeNetwork {
    private List<List<PipeNode>> network;
    PipeNode root;

    public PipeNodeNetwork() {
        this.network = new ArrayList<>();
    }

    public void addListOfNodesToNetwork(List<PipeNode> nodesToAdd) {
        network.add(nodesToAdd);
    }

    public PipeNode get(int row, int col) {
        return network.get(row).get(col);
    }

    public PipeNode getRoot() {
        return root;
    }

    public void setRoot(PipeNode root) {
        this.root = root;
    }

    public List<List<PipeNode>> getNetworkAsList() {
        return network;
    }

    public void printNetwork() {
        for (int row = 0; row < network.size(); row++) {
            System.out.print("Row " + (row) + ": ");
            for (int col = 0; col < network.get(0).size(); col++) {
                System.out.print(get(row, col).getNodeType());
            }
            System.out.println();
        }
    }
}
