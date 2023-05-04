package com.lrw.datastructure.queue;

import com.lrw.datastructure.list.LinkedList;
import com.lrw.datastructure.list.List;

public class Dequeue<E> {

    private List<E> list = new LinkedList<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueueFront(E element) {
        list.add(0, element);
    }

    public void enQueueRear(E element) {
        list.add(element);
    }

    public E deQueueFront() {
        return list.remove(0);
    }

    public E deQueueRear() {
        return list.remove(list.size() - 1);
    }

    public E front() {
        return list.get(0);
    }

    public E rear() {
        return list.get(list.size() - 1);
    }

    public void clear() {
        list.clear();
    }

}
