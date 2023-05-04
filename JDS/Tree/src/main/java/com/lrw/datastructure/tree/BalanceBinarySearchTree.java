package com.lrw.datastructure.tree;

import java.util.Comparator;

public class BalanceBinarySearchTree<E> extends BinarySearchTree<E> {

    public BalanceBinarySearchTree() {
    }

    public BalanceBinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }

    protected void rotateUnify(
            Node<E> r, // 这颗子树的根结点
            Node<E> a, Node<E> b, Node<E> c,
            Node<E> d,
            Node<E> e, Node<E> f, Node<E> g) {

        // 让d成为这颗子树的根结点
        d.parent = r.parent;
        if (r.isRightChild())
            r.parent.right = d;
        else if (r.isLeftChild())
            r.parent.left = d;
        else
            root = d;

        // a-b-c
        b.left = a;
        b.right = c;
        if (a != null) a.parent = b;
        if (c != null) c.parent = b;

        // e-f-g
        f.left = e;
        f.right = g;
        if (e != null) e.parent = f;
        if (g != null) g.parent = f;

        // b-d-f
        d.left = b;
        d.right = f;
        f.parent = d;
        b.parent = d;
    }

    /**
     * 左旋
     * --grand
     * ----\
     * -----\
     * ----parent
     * ----/     \
     * ---/       \
     * --child     n
     *
     * @param grand
     */
    protected void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 右旋
     * -----grand
     * -------/
     * ------/
     * ----parent
     * ----/     \
     * ---/       \
     * --n       child
     *
     * @param grand
     */
    protected void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;

        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 旋转之后更新结点性质
     *
     * @param grand
     * @param parent
     * @param child
     */
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        // 更新parent
        parent.parent = grand.parent;
        if (grand.isLeftChild())
            grand.parent.left = parent;
        else if (grand.isRightChild())
            grand.parent.right = parent;
        else
            root = parent;

        // 更新child
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand
        grand.parent = parent;
    }

}
