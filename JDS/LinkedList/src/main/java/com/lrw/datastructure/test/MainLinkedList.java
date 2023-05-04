package com.lrw.datastructure.test;

import com.lrw.datastructure.list.circle.CircleLinkedList;

public class MainLinkedList {
    public static void main(String[] args) {
        testJosephus();
//        List<Integer> list = new CircleLinkedList<>();
//
//        list.add(11);
//        list.add(22);
//        list.add(33);
//        list.add(44);
//
//        list.add(0, 55); // [55, 11, 22, 33, 44]
//        System.out.println(list.toString());
//        list.add(2, 66); // [55, 11, 66, 22, 33, 44]
//        System.out.println(list.toString());
//        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]
//        System.out.println(list.toString());
//        list.remove(0); // [11, 66, 22, 33, 44, 77]
//        System.out.println(list.toString());
//        list.remove(2); // [11, 66, 33, 44, 77]
//        System.out.println(list.toString());
//        list.remove(list.size() - 1); // [11, 66, 33, 44]
//
//        System.out.println(list);
    }

    /**
     * 约瑟夫问题
     */
    static void testJosephus() {
        CircleLinkedList<Integer> list = new CircleLinkedList();
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }

        list.reset(); // 指向头结点

        while (!list.isEmpty()) {
            list.next();
            list.next();
            System.out.println(list.remove());
        }
    }
}
