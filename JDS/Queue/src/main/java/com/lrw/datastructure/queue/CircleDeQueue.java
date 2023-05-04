package com.lrw.datastructure.queue;

@SuppressWarnings("unchecked")
public class CircleDeQueue<E> {
    private int front; // 队头索引，默认为0
    private int size;
    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    public CircleDeQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * 尾部入队
     *
     * @param element
     */
    public void enQueueRear(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element; // 考虑回到队头的位置，防止+1过界
        size++;
    }

    /**
     * 头部入队
     *
     * @param element
     */
    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        front = index(-1);
        elements[front] = element;
        size++;
    }

    /**
     * 尾部出队
     *
     * @return
     */
    public E deQueueRear() {
        int rearIndex = index(size - 1);
        E rear = elements[rearIndex];
        elements[rearIndex] = null;
        return rear;
    }

    /**
     * 头部出队
     *
     * @return
     */
    public E deQueueFront() {
        E frontEle = elements[front];
        elements[front] = null;
        front = index(1); // 考虑回到队头的位置，防止+1过界
        size--;
        return frontEle;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public E front() {
        return elements[front];
    }

    public E rear() {
        return elements[index(size - 1)];
    }

    private int index(int index) {
        index += front;
        if (index < 0) {
            return index + elements.length;
        }
        return index - (index > elements.length ? elements.length : 0);
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

    public void clear() {
        size = 0;
        front = 0;
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
    }
}
