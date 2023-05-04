package com.lrw.datastructure.set;

public interface Set<E> {

    int size();

    boolean isEmpty();

    void clear();

    boolean contains(E element);

    void remove(E element);

    void traversal(Visitor<E> visitor);

    void add(E element);

    abstract class Visitor<E> {
        boolean stop;

        protected abstract boolean visit(E element);
    }

}
