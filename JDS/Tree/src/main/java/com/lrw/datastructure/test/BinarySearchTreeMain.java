package com.lrw.datastructure.test;

import com.lrw.datastructure.entity.Person;
import com.lrw.datastructure.printer.BinaryTrees;
import com.lrw.datastructure.tree.BinarySearchTree;

import java.util.Comparator;

public class BinarySearchTreeMain {
    private static class PersonComparator implements Comparator<Person> {

        /**
         * @param e1
         * @param e2
         * @return
         */
        @Override
        public int compare(Person e1, Person e2) {
            return e1.getAge() - e2.getAge();
        }
    }

    public static void main(String[] args) {
        test1();
    }

    public static void test2() {
        Integer[] data = new Integer[]{7, 4, 9, 2, 5};
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++)
            binarySearchTree.add(data[i]);
        boolean complete = binarySearchTree.isComplete();
        System.out.println(complete);
    }

    public static void test1() {
        Integer[] data = new Integer[]{
                7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12
        };

        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++)
            binarySearchTree.add(data[i]);

        BinaryTrees.println(binarySearchTree);

//        binarySearchTree.preorderTraversal(element -> System.out.print(element + " "));
//        System.out.println();
//        binarySearchTree.inorderTraversal(element -> System.out.print(element + " "));
//        System.out.println();
//        binarySearchTree.postorderTraversal(element -> System.out.print(element + " "));
//        System.out.println();
//        binarySearchTree.levelOrderTraversal();
//        System.out.println();
//
//        binarySearchTree.levelOrder(element -> System.out.print(element + " "));
        System.out.println();

        System.out.println(binarySearchTree.heightWithNonCursive());
    }
}
