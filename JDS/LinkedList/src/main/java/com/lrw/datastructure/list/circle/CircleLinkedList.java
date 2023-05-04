package com.lrw.datastructure.list.circle;

import com.lrw.datastructure.list.AbstractList;

public class CircleLinkedList<E> extends AbstractList<E> {
    private Node<E> firstNode;

    private Node<E> lastNode;

    // 指向某个结点
    private Node<E> current;

    /**
     * 返回index位置对应的元素
     *
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        return getNode(index).element;
    }

    public void reset() {
        current = firstNode;
    }

    /**
     * current往后走
     *
     * @return
     */
    public E next() {
        if (current == null) return null;
        current = current.next;
        return current.element;
    }

    public E remove() {
        if (current == null) return null;

        Node<E> next = current.next;
        E ele = remove(current);

        if (size == 0) {
            current = null;
        } else
            current = next;

        return ele;
    }

    private E remove(Node<E> node) {
        if (size == 1) {
            firstNode = null;
            lastNode = null;
        } else {
            Node<E> prev = node.prev;
            Node<E> next = node.next;

            // 处理与node相关联的结点
            prev.next = next;
            next.prev = prev;

            if (node == firstNode) { // index == 0
                firstNode = next;
            }

            if (node == lastNode) { // index == size - 1
                lastNode = prev;
            }
        }
        size--;
        return node.element;
    }

    /**
     * 设置index位置的元素
     *
     * @param index
     * @param element
     * @return
     */
    @Override
    public E set(int index, E element) {
        Node<E> node = getNode(index);

        E oldEle = node.element;
        node.element = element;

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
        rangeCheckAdd(index);

        if (index == size) { // 链尾插入
            Node<E> oldLastNode = lastNode;
            //   pre <-- newNode --> next
            lastNode = new Node<>(oldLastNode, element, firstNode); // 新来的要有所指向

            if (oldLastNode == null) { // 添加首元素 (index == size == 0)
                firstNode = lastNode;
                firstNode.prev = firstNode;
                firstNode.next = firstNode;
            } else {
                oldLastNode.next = lastNode;
                firstNode.prev = lastNode;
            }
        } else {
            Node<E> next = getNode(index);
            Node<E> prev = next.prev;

            // prev     <-->      next
            // prev <-- eNode --> next
            Node<E> eNode = new Node<>(prev, element, next); // 中间位置
            next.prev = eNode;
            prev.next = eNode;

            if (next == firstNode) { // index == 0 头插时改变头指针指向
                firstNode = eNode;
            }
        }
        size++;
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
        return remove(getNode(index));
    }

    /**
     * @param element
     */
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
            Node<E> head = firstNode;
            for (int i = 0; i < size; i++) {
                if (head.element == null) return i;
                head = head.next;
            }
        } else {
            Node<E> head = firstNode;
            for (int i = 0; i < size; i++) {
                if (element.equals(head.element)) return i;
                head = head.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 清空所有元素
     */
    @Override
    public void clear() {
        size = 0;
        firstNode = null;
        lastNode = null;
    }

    private static class Node<E> {
        E element;
        Node<E> next; // 后继
        Node<E> prev; // 前驱

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    private Node<E> getNode(int index) {
        rangeCheck(index);

        if (index < (size >> 1)) { // [0, mid)
            Node<E> head = firstNode;
            for (int i = 0; i < index; i++) {
                head = head.next;
            }
            return head;
        } else { // [mid, size]
            Node<E> head = lastNode;
            for (int i = size - 1; i > index; i--) {
                head = head.prev;
            }
            return head;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("size = ").append(size).append(", [");
        Node<E> head = firstNode;
        for (int i = 0; i < size; i++) {
            builder.append(head.element).append(", ");
            head = head.next;
        }
        builder.replace(builder.lastIndexOf(", "), builder.length(), "]");
        return builder.toString();
    }
}
