package com.lrw.datastructure.test;

import com.lrw.datastructure.entity.Key;
import com.lrw.datastructure.entity.Person;
import com.lrw.datastructure.hash.HashMap;
import com.lrw.datastructure.map.Map;

public class MainHashMap {
    public static void main(String[] args) {
//        testPut();
        testCompare();
    }

    private static void testCompare() {
        Map<Object, Integer> map = new HashMap<>();
        for (int i = 1; i <= 19; i++) {
            map.put(new Key(i), i++);
        }

        System.out.println(map.get(new Key(1)));
    }


    private static void testPut() {
        Person p1 = new Person(10, 1.67f, "jack");
        Person p2 = new Person(10, 1.67f, "jack");
        HashMap<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put("jack", 3);
        map.put("rose", 4);
        map.put(null, 5);
        System.out.println(map.size());
//        System.out.println(map.get("jack"));
//        System.out.println(map.get(null));

        System.out.println(map.remove("jack"));
        System.out.println("size: " + map.size());

        System.out.println(map.containsKey(null));
        System.out.println(map.containsValue(1));

        System.out.println("Traversal: ");
        map.traversal(new Map.Visitor<Object, Integer>() {
            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

}
