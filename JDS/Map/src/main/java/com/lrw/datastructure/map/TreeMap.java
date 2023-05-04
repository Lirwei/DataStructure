package com.lrw.datastructure.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Comparator<K> comparator;


    private int size;
    private Node<K, V> root;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        elementNotNullCheck(key);

        // 添加第一个节点
        if (root == null) {
            root = new Node<>(key, value, null);
            size++;
            afterPut(root);
            return null;
        }

        // 添加后续节点，并找到父节点
        Node<K, V> node = root;
        Node<K, V> parent = root;
        int comp = 0;
        while (node != null) {
            // 待插入数据，与当前遍历节点数据进行比较
            comp = compare(key, node.key);
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
            node.key = key;
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        // 看看插入到父节点的哪个位置
        Node<K, V> pNode = new Node<>(key, value, parent);
        if (comp > 0)
            parent.right = pNode;
        else
            parent.left = pNode;
        size++;

        // 新添加结点之后的处理
        afterPut(pNode);
        return null;
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 添加的是根结点 或者 上溢到了根结点
        if (parent == null) {
            setBlack(node);
            return;
        }

        // 如果父结点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父结点
        Node<K, V> uncle = parent.getSibling();
        // 祖父结点
        Node<K, V> grand = setRed(parent.parent);
        // 叔父结点是红色[B树结点上溢]
        if (isRed(uncle)) {
            setBlack(parent);
            setBlack(uncle);

            // 把祖父结点当作是新添加的结点

            afterPut(grand);
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

    /**
     * @param e1
     * @param e2
     * @return = 0, e1 == e2
     * > 0, e1  > e2
     * < 0, e1  < e2
     */
    private int compare(K e1, K e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable) e1).compareTo(e2);
    }

    private void elementNotNullCheck(K key) {
        if (key == null)
            throw new IllegalArgumentException("key must not be null!");
    }

    /**
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    /**
     * @param key
     * @return
     */
    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    private V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;

        V oldValue = node.value;
        if (node.left != null && node.right != null) { // 删除度为2的结点
            // 找到后继结点
            Node<K, V> pNode = successor(node);
            // 用后继结点的值覆盖度为2的结点的值
            node.key = pNode.key;
            node.value = pNode.value;
            // 删除后继结点
            node = pNode;
        }

        // 删除node结点 (node度为1或者0)
        Node<K, V> child = node.left != null ? node.left : node.right;
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
        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {
        // 用以取代node的子结点是红色 || 如果删除的结点是红色，直接返回
        if (isRed(node)) {
            setBlack(node);
            return;
        }

        // 删除结点为黑
        Node<K, V> parent = node.parent;

        // 删除结点为黑色，且为根结点
        if (parent == null) return;

        // 删除结点是黑色叶子结点
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;

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
     * 前驱结点
     *
     * @param node
     * @return
     */
    protected Node<K, V> processor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱结点在左子树当中（left.right.right...）
        if (node.left != null) {
            Node<K, V> p = node.left;
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        // 从父结点、祖父结点中寻找前驱结点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 后继结点
     *
     * @param node
     * @return
     */
    protected Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        Node<K, V> p = node.right;
        // 后继结点在右子树当中（right.left.left...）
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(K key) {
        Node<K, V> node = node(key);
        return node != null;
    }

    /**
     * @param value
     * @return
     */
    @Override
    public boolean containsValue(V value) {
        if (root == null)
            return false;

        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valEquals(value, node.value)) {
                return true;
            }
            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);
        }

        return false;
    }

    /**
     * @param visitor
     */
    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) return;
        traversal(node.left, visitor);
        visitor.visit(node.key, node.value);
        traversal(node.right, visitor);
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    /**
     * 辅助方法：染色
     *
     * @param node
     * @param color
     */
    private Node<K, V> setColor(Node<K, V> node, boolean color) {
        if (node == null) return null;
        node.color = color;
        return node;
    }

    /**
     * 辅助方法：染红色
     *
     * @param node
     * @return
     */
    private Node<K, V> setRed(Node<K, V> node) {
        return setColor(node, RED);
    }

    /**
     * 辅助方法：染黑色
     *
     * @param node
     * @return
     */
    private Node<K, V> setBlack(Node<K, V> node) {
        return setColor(node, BLACK);
    }

    /**
     * 获取node的颜色
     *
     * @param node
     * @return
     */
    private boolean getColor(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * 结点颜色是否为黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<K, V> node) {
        return getColor(node) == BLACK;
    }

    /**
     * 结点颜色是否为红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<K, V> node) {
        return getColor(node) == RED;
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
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
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
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;

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
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
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

    /**
     * 根据元素key获取结点对象
     *
     * @param key
     * @return
     */
    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        int comp;
        while (node != null) {
            comp = compare(key, node.key);
            if (comp == 0) return node;
            if (comp > 0)
                node = node.right;
            else
                node = node.left;
        }
        return null;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        boolean color = RED;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChild() {
            return left != null && right != null;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public Node<K, V> getSibling() {
            if (isLeftChild())
                return parent.right;

            if (isRightChild())
                return parent.left;
            return null;
        }
    }
}
