package com.lrw.datastructure.main;

import com.lrw.datastructure.entity.Person;
import com.lrw.datastructure.list.ArrayList;
import com.lrw.datastructure.list.List;

public class MainArrayList {
    public static void main(String[] args) {
//        List<Integer> arrayList = new ArrayList();
//
//        for (int i = 0; i < 30; i++) {
//            arrayList.add(i);
//        }
//
//        System.out.println(arrayList);
////        arrayList.remove(arrayList.size() - 1);
//        arrayList.add(arrayList.size() - 1, 10);
//        System.out.println(arrayList);
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Tom", 10));
        persons.clear();

        // 提醒JVM进行垃圾回收
        System.gc();
        //
//        System.out.println(persons);
    }
}