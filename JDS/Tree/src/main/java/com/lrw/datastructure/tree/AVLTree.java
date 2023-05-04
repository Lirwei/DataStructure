package com.lrw.datastructure.tree;

import java.util.Comparator;

public class AVLTree<E> extends BalanceBinarySearchTree<E> {

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 处理恢复平衡的逻辑
     *
     * @param node 新添加结点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 平衡、更新高度
                updateHeight(node);
            } else {
                // 恢复平衡操作
//                reBalance(node);
                reBalanceUnify(node);
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 平衡、更新高度
                updateHeight(node);
            } else {
                // 恢复平衡操作
//                reBalance(node);
                reBalanceUnify(node);
            }
        }
    }

    /**
     * 判断当前结点是否平衡
     *
     * @param node 当前结点
     * @return true is balance, else false
     */
    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }

    /**
     * 统一旋转
     */
    private void reBalanceUnify(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();

        // L
        if (parent.isLeftChild()) {
            // LL
            if (node.isLeftChild()) {
                rotateUnify(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            }
            // LR
            else {
                rotateUnify(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
            }
        }
        // R
        else {
            // RL
            if (node.isLeftChild()) {
                rotateUnify(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);
            }
            // RR
            else {
                rotateUnify(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
            }
        }
    }

    @Override
    protected void rotateUnify(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
        super.rotateUnify(r, a, b, c, d, e, f, g);
        // 更新高度
        updateHeight(b);
        updateHeight(f);
        updateHeight(d);
    }

    /**
     * 使树恢复平衡
     *
     * @param grand 高度最低的不平衡结点
     */
    private void reBalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();

        // L
        if (parent.isLeftChild()) {
            // LL
            if (node.isLeftChild()) {
                rotateRight(grand);
            }
            // LR
            else {
                rotateLeft(parent);
                rotateRight(grand);
            }
        }
        // R
        else {
            // RL
            if (node.isLeftChild()) {
                rotateRight(parent);
                rotateLeft(grand);
            }
            // RR
            else {
                rotateLeft(grand);
            }
        }
    }

    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);
        // 更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * 更新当前结点的高度
     *
     * @param node
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 平衡因子
         *
         * @return
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        /**
         * 更新当前结点的高度
         */
        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;

            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;

            return isLeftChild() ? left : right;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null)
                parentString = parent.element.toString();
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }
}
