package com.lrw.datastructure.queue;

public class CircleQueue<E> {
    private int front; // 队头索引，默认为0
    private int size;
    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enQueue(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element; // 考虑回到队头的位置，防止+1过界
        size++;
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        // 当前元素数量不超过数组大小
        if (oldCapacity > capacity) return;

        // 在原数组的基础上扩容1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;

        // 重置front
        front = 0;
    }

    public E deQueue() {
        E frontEle = elements[front];
        elements[front] = null;
        front = index(1); // 考虑回到队头的位置，防止+1过界
        size--;
        return frontEle;
    }

    public E front() {
        return elements[front];
    }

    private int index(int index) {
        index += front;
        return index - (index >= elements.length ? elements.length : 0);
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;
    }
}
