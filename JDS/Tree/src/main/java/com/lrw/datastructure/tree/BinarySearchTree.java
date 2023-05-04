package com.lrw.datastructure.tree;

import java.util.Comparator;

public class BinarySearchTree<E> extends BinaryTree<E> {
    private final Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 二叉树翻转
     *
     * @return
     */
    public Node<E> invertTree() {
        return invertTree(root);
    }

    /**
     * 先序遍历方式翻转
     *
     * @param node
     * @return
     */
    private Node<E> invertTree(Node<E> node) {
        if (node == null)
            return null;

        Node<E> tmp = node.left;
        node.left = node.right;
        node.right = tmp;

        invertTree(node.left);
        invertTree(node.right);

        return node;
    }

    /**
     * 中序遍历方式翻转
     *
     * @param node
     * @return
     */
    private Node<E> invertTreeIn(Node<E> node) {
        if (node == null) return null;

        invertTree(node.left);

        Node<E> tmp = node.left;
        node.left = node.right;
        node.right = tmp;

        invertTree(node.left);
        return node;
    }

    /**
     * 后序遍历方式翻转
     *
     * @param node
     * @return
     */
    private Node<E> invertTreePost(Node<E> node) {
        if (node == null) return null;

        invertTree(node.left);
        invertTree(node.right);

        Node<E> tmp = node.left;
        node.left = node.right;
        node.right = tmp;

        return node;
    }

    public void add(E element) {
        elementNotNullCheck(element);

        // 添加第一个节点
        if (root == null) {
            root = createNode(element, null);
            size++;
            afterAdd(root);
            return;
        }

        // 添加后续节点，并找到父节点
        Node<E> node = root;
        Node<E> parent = root;
        int comp = 0;
        while (node != null) {
            // 待插入数据，与当前遍历节点数据进行比较
            comp = compare(element, node.element);
            parent = node;
            if (comp > 0) {
                node = node.right;
                continue;
            }

            if (comp < 0) {
                node = node.left;
                continue;
            }

            // comp == 0，覆盖
            node.element = element;
            return;
        }

        // 看看插入到父节点的哪个位置
        Node<E> pNode = createNode(element, parent);
        if (comp > 0)
            parent.right = pNode;
        else
            parent.left = pNode;
        size++;

        // 新添加结点之后的处理
        afterAdd(pNode);
    }

    /**
     * 添加node之后的调整
     *
     * @param node 新添加结点
     */
    protected void afterAdd(Node<E> node) {
        // TODO 默认即可，由AVLTree实现
    }

    /**
     * @param e1
     * @param e2
     * @return = 0, e1 == e2
     * > 0, e1  > e2
     * < 0, e1  < e2
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable) e1).compareTo(e2);
    }

    public void remove(E element) {
        remove(node(element));
    }

    public boolean contains(E element) {
        return node(element) != null;
    }

    private void remove(Node<E> node) {
        if (node == null) return;
        size--;

        if (node.left != null && node.right != null) { // 删除度为2的结点
            // 找到后继结点
            Node<E> pNode = successor(node);
            // 用后继结点的值覆盖度为2的结点的值
            node.element = pNode.element;
            // 删除后继结点
            node = pNode;
        }

        // 删除node结点 (node度为1或者0)
        Node<E> child = node.left != null ? node.left : node.right;
        if (child != null) { // 度为1
            // 更改parent
            child.parent = node.parent;
            // node为根结点
            if (node.parent == null) {
                root = child;
            }
            // 子结点替代父结点：更改parent的right、left指向
            else if (node == node.parent.left) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
            afterRemove(child);
        }
        // node度为0 且为根结点
        else if (node.parent == null) {
            root = null;
            afterRemove(node);
        }
        // node度为0，且为叶子结点
        else {
            // node父结点确定node为左树时，左树置空
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

            afterRemove(node);
        }
    }

    protected void afterRemove(Node<E> node) {
        // TODO AVLTree中重写
    }

    /**
     * 根据元素element获取结点对象
     *
     * @param element
     * @return
     */
    private Node<E> node(E element) {
        Node<E> node = root;
        int comp;
        while (node != null) {
            comp = compare(element, node.element);
            if (comp == 0) return node;
            if (comp > 0)
                node = node.right;
            else
                node = node.left;
        }
        return null;
    }

    /**
     * 元素不能为空
     *
     * @param element != null
     */
    private void elementNotNullCheck(E element) {
        if (element == null)
            throw new IllegalArgumentException("element must not be null!");
    }
}
