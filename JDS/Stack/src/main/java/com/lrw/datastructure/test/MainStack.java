package com.lrw.datastructure.test;

import com.lrw.datastructure.stack.Stack;

import java.util.HashMap;

public class MainStack {
    private static HashMap<Character, Character> map = new HashMap<>();

    static {
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 有效括号
     *
     * @param str 源数据
     */
    static boolean isValid(String str) {
        Stack<Character> stack = new Stack<>();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            // if (c == '(' || c == '{' || c == '[') { // 左字符入栈
            if (map.containsKey(c)) { // 左字符入栈
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;

                Character pop = stack.pop();
                // if ((pop == '(' && c != ')') || (pop == '[' && c != ']') || (pop == '{' && c != '}')) return false;
                if (map.get(pop) != c) return false;
            }
        }
        return true;
    }

}
