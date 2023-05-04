package com.lrw.datastructure.set;

import com.lrw.datastructure.list.LinkedList;
import com.lrw.datastructure.list.List;

public class ListSet<E> implements Set<E> {
    private List<E> list = new LinkedList<>();

    /**
     * @return
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     *
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * @param element
     * @return
     */
    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    /**
     * @param element
     */
    @Override
    public void remove(E element) {
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND) {
            list.set(index, element);
        }

    }

    /**
     * @param visitor
     */
    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) return;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (visitor.visit(list.get(i)))
                return;
        }
    }

    /**
     * @param element
     */
    @Override
    public void add(E element) {
        if (list.contains(element)) return;
        list.add(element);
    }
}
