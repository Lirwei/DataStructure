## 栈

> 原则：Last In First Out

```java
public interface Stack {
    int size(); // 元素的数量
    boolean isEmpty(); // 是否为空
    void push(E ele); // 入栈
    E pop(); // 出栈
    E top(); // 获取栈顶元素
    void clear(); // 清空栈
}
```

### 栈的应用
#### （1）验证括号有效性

```Java
package org.example;

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
        // TODO isValid(str)
    }

    /**
     * 有效括号
     * @param str
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
```

#### （2）表达式