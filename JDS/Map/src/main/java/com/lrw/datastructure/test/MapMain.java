package com.lrw.datastructure.test;

import com.lrw.datastructure.map.Map;
import com.lrw.datastructure.map.TreeMap;

public class MapMain {
    public static void main(String[] args) {
        testAdd();
    }

    public static void testAdd() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("class", 2);
        map.put("public", 5);
        map.put("text", 6);
        map.put("public", 8);

        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }
}
