package com.lrw.datastructure.list;

public class ArrayList<E> extends AbstractList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 所有的元素
     */
    private E[] elements;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capacity];
    }

    /**
     * 返回index位置对应的元素
     *
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 设置index位置的元素
     *
     * @param index
     * @return
     */
    @Override
    public E set(int index, E element) {
        rangeCheck(index);

        E oldEle = elements[index];
        elements[index] = element;
        return oldEle;
    }

    /**
     * 往index位置添加元素
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        // 当index = size时，相当于尾插
        rangeCheckAdd(index);

        ensureCapacity(size + 1);

        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        size++;
    }

    /**
     * 保证要有capacity的容量
     *
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length; // 申请数组的大小
        if (oldCapacity >= capacity) return;

        // 扩容1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;

//        System.out.println("old capacity: " + oldCapacity + ", new capacity: " + newCapacity);
    }

    /**
     * 删除index位置对应的元素
     *
     * @param index
     * @return
     */
    @Override
    public E remove(int index) {
        rangeCheck(index);

        E oldEle = elements[index];
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }

        elements[--size] = null;

        trim();
        return oldEle;
    }

    @Override
    public void remove(E element) {
        remove(indexOf(element));
    }

    /**
     * 查看元素的位置
     *
     * @param element
     * @return
     */
    @Override
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) return i;
            }
        }

        return ELEMENT_NOT_FOUND;
    }

    /**
     * 清空所有元素
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;

        if (elements != null && elements.length > DEFAULT_CAPACITY) {
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        }
    }

    /**
     * 缩容
     */
    private void trim() {
        int capacity = elements.length;
        // 不需缩容
        if (size >= (capacity >> 1) || capacity <= DEFAULT_CAPACITY) return;

        // 需缩容
        int newCapacity = capacity >> 1;
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     * 打印数组
     *
     * @return
     */
    @Override
    public String toString() {
        // size = 3, [99, 98, 97]
        StringBuilder builder = new StringBuilder();
        builder.append("size = ").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(elements[i]);
//            if (i != size -1) {
//                builder.append(", ");
//            }
        }
        builder.append("]");
        return builder.toString();
    }
}
