package com.lrw.datastructure.set;

import com.lrw.datastructure.tree.BinaryTree;
import com.lrw.datastructure.tree.RBTree;

import java.util.Comparator;

public class TreeSet<E> implements Set<E> {
    private RBTree<E> tree;

    public TreeSet() {
        this(null);
    }

    public TreeSet(Comparator<E> comparator) {
        tree = new RBTree<>(comparator);
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return tree.size();
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    /**
     *
     */
    @Override
    public void clear() {
        tree.clear();
    }

    /**
     * @param element
     * @return
     */
    @Override
    public boolean contains(E element) {
        return false;
    }

    /**
     * @param element
     */
    @Override
    public void remove(E element) {
        tree.remove(element);
    }

    /**
     * @param visitor
     */
    @Override
    public void traversal(Visitor<E> visitor) {
        tree.inorderTraversal(new BinaryTree.Visitor<E>() {
            @Override
            public boolean visit(E element) {
                visitor.visit(element);
                return false;
            }
        });
    }

    /**
     * @param element
     */
    @Override
    public void add(E element) {
        tree.add(element);
    }
}
