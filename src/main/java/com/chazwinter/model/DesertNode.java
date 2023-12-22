package com.chazwinter.model;

public class DesertNode {
    private String value;
    private DesertNode left;
    private DesertNode right;

    public DesertNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DesertNode getLeft() {
        return left;
    }

    public void setLeft(DesertNode left) {
        this.left = left;
    }

    public DesertNode getRight() {
        return right;
    }

    public void setRight(DesertNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format("Node %s: (l: %s, r: %s)", value, left.value, right.value);
    }
}
