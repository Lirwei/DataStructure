package com.lrw.datastructure.test;

import com.lrw.datastructure.printer.BinaryTrees;
import com.lrw.datastructure.tree.RBTree;

public class RBTreeMain {
    public static void main(String[] args) {
//        testAdd();
        testRemove();
    }

    private static void testRemove() {
        Integer[] data = new Integer[]{
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };

        RBTree<Integer> rb = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
        }

        BinaryTrees.println(rb);
        System.out.println("---------------------------");

        for (int i = 0; i < data.length; i++) {
            System.out.println("[" + data[i] + "]");
            rb.remove(data[i]);
            BinaryTrees.println(rb);
            System.out.println("---------------------------");
        }
    }

    private static void testAdd() {
        Integer[] data = new Integer[]{
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };

        RBTree<Integer> rb = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
            System.out.println("[" + data[i] + "]");
            BinaryTrees.println(rb);
            System.out.println("---------------------------");
        }
    }
}
