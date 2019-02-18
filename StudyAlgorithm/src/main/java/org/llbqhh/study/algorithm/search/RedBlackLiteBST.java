package org.llbqhh.study.algorithm.search;

import java.util.LinkedList;
import java.util.Queue;

/******************************************************************************
 *  Compilation:  javac RedBlackLiteBST.java
 *  Execution:    java RedBlackLiteBST < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/33balanced/tinyST.txt
 *
 *  A symbol table implemented using a left-leaning red-black BST.
 *  This is the 2-3 version.
 *
 *  This implementation implements only put, get, and contains.
 *  See RedBlackBST.java for a full implementation including delete.
 *
 ******************************************************************************/

/**
 * 简单的红黑树实现,只实现了put\get\contains方法
 * @param <K>
 * @param <V>
 */
public class RedBlackLiteBST<K extends Comparable<K>, V> {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST
    private int n;         // number of key-value pairs in BST

    // BST helper node data type
    private class Node {
        private K key;           // key
        private V val;         // associated data
        private Node left;         // links to left subtrees
        private Node right;        // links to right subtrees
        private boolean color;     // color of parent link

        public Node(K key, V val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }

    /***************************************************************************
     *  Standard BST search. 和二叉查找树相同的部分(get)
     ***************************************************************************/

    // return value associated with the given key, or null if no such key exists
    public V get(K key) {
        return get(root, key);
    }
    public V get(Node x, K key) {
        // 和BinarySearchTree的get实现方式不同的地方是,这里使用循环
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    // is there a key-value pair in the symbol table with the given key?
    public boolean contains(K key) {
        return get(key) != null;
    }

    /***************************************************************************
     *  Red-black tree insertion.
     ***************************************************************************/

    public void put(K key, V val) {
        root = insert(root, key, val);
        // 根节点永远是黑色,如果是从红色变为黑色,则树的高度加1
        root.color = BLACK;
    }

    private Node insert(Node h, K key, V val) {
        if (h == null) {
            // 这个实现中简单使用n来标志树的总节点数
            n++;
            // 新加入的节点都为红色,目的为创建一个3-节点,然后进行红色额节点的上移,这样才不会对树的平衡性产生影响
            return new Node(key, val, RED);
        }

        // 和普通的二叉查找树逻辑相同,首先找到需要插入(或更新)的位置,然后创建一个新的红色节点
        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = insert(h.left,  key, val);
        else if (cmp > 0) h.right = insert(h.right, key, val);
        else              h.val   = val;

        // fix-up any right-leaning links
        // 通过左旋,右旋,改变颜色来重新平衡这颗树,
        // 即构造出沿左子树的连续两个红节点,
        // 然后再通过右旋将其变为一个黑根节点带两个红色子节点的形状,然后再通过变色将红色节点上移
        // 可以参考https://algs4.cs.princeton.edu/33balanced/

        // h的右节点红,左节点黑,则进行左旋
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        // h的左节点红,左节点的左节点也为红(即沿左子树的连续两个红节点),则进行右旋
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        // h的左右节点都红(这时h一定为黑),则改变颜色,将两个子节点颜色设为黑色,h设为红色
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);

        return h;
    }

    /***************************************************************************
     *  Red-black tree helper functions.
     ***************************************************************************/

    // is node x red (and non-null) ?
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // 左旋,可以画一个共6个节点的图来帮助理解
    private Node rotateLeft(Node h) {
        assert (h != null) && isRed(h.right);
        // h的右节点成为新的旋转后的子树的根节点
        Node x = h.right;
        // h的右节点指向x的左节点(即原来的h的右节点的左节点)
        h.right = x.left;
        // x的左节点指向h
        x.left = h;
        // x的颜色设置为h的颜色
        x.color = h.color;
        // h的颜色设为红色
        h.color = RED;
        return x;
    }

    // 右旋,和左旋逻辑类似
    private Node rotateRight(Node h) {
        assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // precondition: two children are red, node is black
    // postcondition: two children are black, node is red
    // 将h的左右节点颜色设置为黑色,h的颜色设置为红色
    // 这个操作在h的两个子节点都为红色,并且h本身为黑色时才会调用
    private void flipColors(Node h) {
        assert !isRed(h) && isRed(h.left) && isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /***************************************************************************
     *  Utility functions.
     ***************************************************************************/
    // return number of key-value pairs in symbol table
    public int size() {
        return n;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // height of tree (1-node tree has height 0)
    public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    // return the smallest key; null if no such key
    public K min() { return min(root); }
    private K min(Node x) {
        K key = null;
        // 和BinarySearchTree实现方式不同的地方在于使用循环而不是递归
        while (x != null) {
            key = x.key;
            x = x.left;
        }
        return key;
    }

    // return the largest key; null if no such key
    public K max() { return max(root); }
    private K max(Node x) {
        K key = null;
        // 和BinarySearchTree实现方式不同的地方在于使用循环而不是递归
        while (x != null) {
            key = x.key;
            x = x.right;
        }
        return key;
    }

    /***************************************************************************
     *  Iterate using an inorder traversal.
     *  Iterating through N elements takes O(N) time.
     ***************************************************************************/
    public Iterable<K> keys() {
        Queue<K> queue = new LinkedList<>();
        keys(root, queue);
        return queue;
    }

    private void keys(Node x, Queue<K> queue) {
        if (x == null) return;
        keys(x.left, queue);
        queue.add(x.key);
        keys(x.right, queue);
    }
}