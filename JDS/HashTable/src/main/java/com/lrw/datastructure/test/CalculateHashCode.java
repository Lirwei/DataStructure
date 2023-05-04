package com.lrw.datastructure.test;

import com.lrw.datastructure.entity.Person;

import java.util.HashMap;
import java.util.Map;

public class CalculateHashCode {
    public static void main(String[] args) {
//        calculateByJDK();
//        calculateString();

        calculatePerson();
    }

    private static void calculatePerson() {
        Person p1 = new Person(10, 1.67f, "jack");
        Person p2 = new Person(10, 1.67f, "jack");

//        System.out.println(p1.hashCode());
//        System.out.println(p2.hashCode());

        Map<Object, Object> map = new HashMap<>();
        map.put(p1, "abc");
        map.put("da", "");
        map.put(p2, "def");

        System.out.println(map.size());
    }

    private static void calculateString() {
        String string = "jack";
        int length = string.length();
        int hashCode = 0;
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            hashCode = hashCode * 31 + c;
            // 31 * i = (2^5 - 1) * i = i * 2^5 - i = (i << 5) - i
//            hashCode = (hashCode << 5) - hashCode + c;
        }

        System.out.println(hashCode);
    }

    private static void calculateByJDK() {
        Integer a = 110;
        Float b = 10.6f;
        Long c = 1561L;
        Double d = 10.9;
        String e = "jack";
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
        System.out.println(d.hashCode());
        System.out.println(e.hashCode());
    }
}
