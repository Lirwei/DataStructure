package com.lrw.datastructure.set;

import com.lrw.datastructure.map.Map;
import com.lrw.datastructure.map.TreeMap;

public class TreeMapSet<E> implements Set<E> {

    Map<E, Object> map = new TreeMap<>();

    /**
     * @return
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     *
     */
    @Override
    public void clear() {
        map.clear();
    }

    /**
     * @param element
     * @return
     */
    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    /**
     * @param element
     */
    @Override
    public void remove(E element) {
        map.remove(element);
    }

    /**
     * @param visitor
     */
    @Override
    public void traversal(Visitor<E> visitor) {
        map.traversal(new Map.Visitor<E, Object>() {
            @Override
            public boolean visit(E key, Object value) {
                visitor.visit(key);
                return false;
            }
        });
    }

    /**
     * @param element
     */
    @Override
    public void add(E element) {
        map.put(element, null);
    }
}
