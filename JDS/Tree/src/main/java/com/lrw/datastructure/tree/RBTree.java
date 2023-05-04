package com.lrw.datastructure.tree;

import java.util.Comparator;

public class RBTree<E> extends BalanceBinarySearchTree<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * @param node 新添加结点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        // 添加的是根结点 或者 上溢到了根结点
        if (parent == null) {
            setBlack(node);
            return;
        }

        // 如果父结点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父结点
        Node<E> uncle = parent.getSibling();
        // 祖父结点
        Node<E> grand = setRed(parent.parent);
        // 叔父结点是红色[B树结点上溢]
        if (isRed(uncle)) {
            setBlack(parent);
            setBlack(uncle);

            // 把祖父结点当作是新添加的结点

            afterAdd(grand);
            return;
        }

        // 叔父结点不是红色
        // L
        if (parent.isLeftChild()) {
            // LL
            if (node.isLeftChild()) {
                setBlack(parent);
            }
            // LR
            else {
                setBlack(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        }
        // R
        else {
            // RR
            if (node.isRightChild()) {
                setBlack(parent);
            }
            // RL
            else {
                setBlack(node);
                rotateRight(parent);
            }
            rotateLeft(grand);
        }

    }

    @Override
    protected void afterRemove(Node<E> node) {
        // 用以取代node的子结点是红色 || 如果删除的结点是红色，直接返回
        if (isRed(node)) {
            setBlack(node);
            return;
        }

        // 删除结点为黑
        Node<E> parent = node.parent;

        // 删除结点为黑色，且为根结点
        if (parent == null) return;

        // 删除结点是黑色叶子结点
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;

        // 被删除的结点在左边，兄弟结点在右边
        if (left) {
            if (isRed(sibling)) {
                setBlack(sibling);
                setRed(parent);
                rotateLeft(parent);

                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟结点的子结点都是黑色
            if (isBlack(sibling.right) && isBlack(sibling.left)) {
                // 兄弟结点没有一个红色子结点，父结点要下溢
                boolean parentColor = isBlack(parent);
                setBlack(parent);
                setRed(sibling);

                if (parentColor)
                    afterRemove(parent);
            }
            // 兄弟结点的子即诶单至少有一个红色子结点
            else {
                // 兄弟结点的左节点是黑色，兄弟结点进行左旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                setColor(sibling, getColor(parent));
                setBlack(sibling.right);
                setBlack(parent);

                rotateLeft(parent);
            }
        }
        // 被删除的结点在右边，兄弟结点在左边
        else {
            if (isRed(sibling)) {
                setBlack(sibling);
                setRed(parent);
                rotateRight(parent);

                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟结点的子结点都是黑色
            if (isBlack(sibling.right) && isBlack(sibling.left)) {
                // 兄弟结点没有一个红色子结点，父结点要下溢
                boolean parentColor = isBlack(parent);
                setBlack(parent);
                setRed(sibling);

                if (parentColor)
                    afterRemove(parent);
            }
            // 兄弟结点的子即诶单至少有一个红色子结点
            else {
                // 兄弟结点的左节点是黑色，兄弟结点进行左旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                setColor(sibling, getColor(parent));
                setBlack(sibling.left);
                setBlack(parent);

                rotateRight(parent);
            }
        }
    }

    /**
     * 辅助方法：染色
     *
     * @param node
     * @param color
     */
    private Node<E> setColor(Node<E> node, boolean color) {
        if (node == null) return null;
        ((RBNode<E>) node).color = color;
        return node;
    }

    /**
     * 辅助方法：染红色
     *
     * @param node
     * @return
     */
    private Node<E> setRed(Node<E> node) {
        return setColor(node, RED);
    }

    /**
     * 辅助方法：染黑色
     *
     * @param node
     * @return
     */
    private Node<E> setBlack(Node<E> node) {
        return setColor(node, BLACK);
    }

    /**
     * 获取node的颜色
     *
     * @param node
     * @return
     */
    private boolean getColor(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    /**
     * 结点颜色是否为黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<E> node) {
        return getColor(node) == BLACK;
    }

    /**
     * 结点颜色是否为红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<E> node) {
        return getColor(node) == RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    private static class RBNode<E> extends Node<E> {
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) str = "R_";
            return str + element.toString();
        }
    }
}