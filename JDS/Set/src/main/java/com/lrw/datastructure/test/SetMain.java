package com.lrw.datastructure.test;

import com.lrw.datastructure.set.Set;
import com.lrw.datastructure.set.TreeSet;
import com.lrw.datastructure.set.ListSet;

public class SetMain {

    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        Set<Integer> listSet = new TreeSet<>();
        listSet.add(10);
        listSet.add(11);
        listSet.add(11);
        listSet.add(11);
        listSet.add(13);
        listSet.add(10);

        listSet.traversal(new Set.Visitor<Integer>() {
            /**
             * @param element
             * @return
             */
            @Override
            protected boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    public static void test1() {
        Set<Integer> listSet = new ListSet<>();
        listSet.add(10);
        listSet.add(11);
        listSet.add(11);
        listSet.add(11);
        listSet.add(13);
        listSet.add(10);

        listSet.traversal(new Set.Visitor<Integer>() {
            /**
             * @param element
             * @return
             */
            @Override
            protected boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

}
