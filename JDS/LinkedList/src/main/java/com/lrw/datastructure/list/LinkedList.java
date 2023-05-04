package com.lrw.datastructure.list;

public class LinkedList<E> extends AbstractList<E> {
    private Node<E> firstNode;

    private Node<E> lastNode;

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
            lastNode = new Node<>(oldLastNode, element, null);

            if (oldLastNode == null) { // 添加首元素 (index == size == 0)
                firstNode = lastNode;
            } else {
                oldLastNode.next = lastNode;
            }
        } else {
            Node<E> next = getNode(index);
            Node<E> prev = next.pre;

            Node<E> eNode = new Node<>(prev, element, next);
            next.pre = eNode;

            if (prev == null) { // index == 0
                firstNode = eNode;
            } else {
                prev.next = eNode;
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

        Node<E> node = getNode(index);
        Node<E> prev = node.pre;
        Node<E> next = node.next;

        if (prev == null) { // index == 0
            firstNode = next;
        } else {
            prev.next = next; // 0 < index < (size - 1)
        }

        if (next == null) { // index == size - 1
            lastNode = prev;
        } else {
            next.pre = prev; // 0 < index < (size - 1)
        }

        size--;
        return node.element;
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
        Node<E> pre; // 前驱

        public Node(Node<E> pre, E element, Node<E> next) {
            this.pre = pre;
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
                head = head.pre;
            }
            return head;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("size = ").append(size).append(", [");
        Node<E> head = firstNode;
        while (head != null) {
            builder.append(head.element).append(", ");
            head = head.next;
        }
        builder.replace(builder.lastIndexOf(", "), builder.length(), "]");
        return builder.toString();
    }
}
