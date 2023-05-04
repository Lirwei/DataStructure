package com.lrw.datastructure.test;

import com.lrw.datastructure.printer.BinaryTrees;
import com.lrw.datastructure.tree.AVLTree;

public class AVLTreeMain {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test1() {
        Integer[] data = new Integer[]{
                85, 19, 69, 3, 7, 99, 95, 2, 1, 70, 44, 58, 11, 21, 14, 93, 57, 4, 56
        };

        AVLTree<Integer> avl = new AVLTree<>();
        for (Integer datum : data) {
            avl.add(datum);
        }

        BinaryTrees.println(avl);
    }

    private static void test2() {
        Integer[] data = new Integer[]{
                85, 19, 69, 3, 7, 99, 95
        };

        AVLTree<Integer> avl = new AVLTree<>();
        for (Integer datum : data) {
            avl.add(datum);
        }

        BinaryTrees.println(avl);
        avl.remove(99);
        avl.remove(85);
        avl.remove(95);
        BinaryTrees.println(avl);

    }


}
