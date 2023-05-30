package ru.itis.kpfu.skvortsova.aatree;

public class AANode {
    AANode left, right;
    int element, level;

    public AANode() {
        this.element = 0;
        this.left = this;
        this.right = this;
        this.level = 0;
    }

    public AANode(int ele) {
        this(ele, null, null);
    }

    public AANode(int ele, AANode left, AANode right) {
        this.element = ele;
        this.left = left;
        this.right = right;
        this.level = 1;
    }
}
