package org.llbqhh.study.algorithm.search;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * 一颗二叉查找树（BST）是一颗二叉树，
 * 其中每个节点都含有一个Comparable的键（以及相关联的值）
 * 且每个节点的键都大于其左子树中的任意节点的键而小于右子树的任意节点的键
 */
public class BinarySearchTree<K extends Comparable<K>, V> {
    private Node root;             // root of BST

    private class Node {
        private K key;           // sorted by key
        private V val;         // associated data
        private Node left;         // left subtrees
        private Node right;        // right subtrees
        private int size;          // number of nodes in subtree

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BinarySearchTree() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public boolean contains(K key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * get，put等方法都是一个public一个private成对出现，便于内部递归操作节点
     * @param key
     * @return
     */
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        // 查找时类似二分查找
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        //如果最终没有找到相等的key则返回null
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        //如果key小于node节点，则从左子树查找
        if      (cmp < 0) return get(x.left, key);
        //大于则从右子树查找
        else if (cmp > 0) return get(x.right, key);
        //等于则返回node节点
        else              return x.val;
    }

    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        // [最后看这行]这行在最终确定了新节点位置后调用,创建一个新的节点,这个节点会被设为上一次的子树
        if (x == null) return new Node(key, val, 1);
        // 将要插入的key和节点的key相比较
        int cmp = key.compareTo(x.key);
        // 如果小于节点的key,则继续递归调用方法,传入左子树(目的在于找到新节点的位置)
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        // 如果大于节点的key,则继续递归调用方法,传入右子树(目的在于找到新节点的位置)
        else if (cmp > 0) x.right = put(x.right, key, val);
        // 如果等于节点的key,说明已经存在,更新值即可
        else              x.val   = val;
        // 递归结束后,依次更新节点的size值
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Removes the smallest key and associated value from the symbol table.
     * 删除最小节点(删除最大节点实现类似)
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        // [最后看这行]一直递归找到没有左子树的节点(即最小节点),将其右子树返回(这个右子树会被设为上一层的左子树,原左子树会被垃圾回收)
        // 当然x.right可能为null,即x没有子节点
        if (x.left == null) return x.right;
        // 递归寻找最小的节点
        x.left = deleteMin(x.left);
        // 递归结束后依次更新节点size
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        // 递归寻找key的位置,并将x的左(右)节点替换成[已经删除了key键所在节点的子树]
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            // 找到key所在节点x后,如果只有一个子树,则返回另一个子树(原来指向节点x的指针将指向返回的这个子树,节点x被垃圾回收)
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            // 如果有两个子树,则用t存储对原来的x节点的指针
            Node t = x;
            // 将x节点重新指向t的右子树中的最小节点
            x = min(t.right);
            // x节点的右子树重新指向删除过最小节点后的t的右子树(t的右子树的最小节点已经成为新的x节点)
            x.right = deleteMin(t.right);
            // x的左子树不变仍旧指向t的左子树
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        // 最后返回的x,
        // 如果是在找到key所在节点后,则是返回已经被替换过的节点.
        // 如果是在递归途中,则还是返回的x本身.
        return x;
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        // 递归寻找最小节点(即最终找到没有左子树的节点)
        if (x.left == null) return x;
        else                return min(x.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        // 递归寻找最大节点(即最终找到没有右子树的节点)
        if (x.right == null) return x;
        else                 return max(x.right);
    }

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public K floor(K key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node floor(Node x, K key) {
        if (x == null) return null;
        // 比较key和节点x
        int cmp = key.compareTo(x.key);
        // 如果相等则返回x
        if (cmp == 0) return x;
        // 如果key在节点x的左子树,则合适的节点一定在x的左子树,并递归调用floor方法获取最终的值
        if (cmp <  0) return floor(x.left, key);
        // 如果key在节点x的右子树,则有可能有合适的值,递归调用floor方法
        Node t = floor(x.right, key);
        // 如果可以在x的右子树中找到合适的节点t,则返回t,否则返回当前节点x
        if (t != null) return t;
        else return x;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param  key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public K ceiling(K key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) return null;
        else return x.key;
    }

    /**
     * 和floor方法实现方法类似,只是反过来
     * @param x
     * @param key
     * @return
     */
    private Node ceiling(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }
        return ceiling(x.right, key);
    }

    /**
     * Return the key in the symbol table whose rank is {@code k}.
     * This is the (k+1)st smallest key in the symbol table.
     *
     * @param  k the order statistic
     * @return the key in the symbol table of rank {@code k}
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *        <em>n</em>–1
     */
    public K select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }
        Node x = select(root, k);
        return x.key;
    }

    // Return key of rank k.
    private Node select(Node x, int k) {
        if (x == null) return null;
        // 用左子树的size来判断
        int t = size(x.left);
        // 如果size大于k,则第k个节点在左子树中,则在左子树继续查找
        if      (t > k) return select(x.left,  k);
        // 如果size小于k,则第k个节点在右子树中,则在右子树继续查找,注意传入的k值为k-t-1(t为左子树的size,1为根节点所占的1位)
        else if (t < k) return select(x.right, k - t - 1);
        // 如果size等于k,则这个节点就是所查找的节点,返回
        else            return x;
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(K key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    /**
     * 返回指定key在二叉树中的排位(有可能这个key并不在节点中)
     * @param key
     * @param x
     * @return
     */
    private int rank(K key, Node x) {
        if (x == null) return 0;
        // key和节点x比较
        int cmp = key.compareTo(x.key);
        // 如果key在节点x的左子树,则递归调用rank
        if      (cmp < 0) return rank(key, x.left);
        // 如果key在节点x的右子树,则key的排位等于他在右子树中的排位加上左子树的size再加上根节点的1
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        // 如果key就在节点x,则返回节点x的左子树的size,即为key的排位
        else              return size(x.left);
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<K> keys() {
        if (isEmpty()) return new LinkedList<>();
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public Iterable<K> keys(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<K> queue = new LinkedList<K>();
        keys(root, queue, lo, hi);
        return queue;
    }

    /**
     * 取得lo和hi之间的所有key
     * @param x
     * @param queue
     * @param lo
     * @param hi
     */
    private void keys(Node x, Queue<K> queue, K lo, K hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        // 如果lo在节点x的左子树,则到节点x的左子树中继续查找
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        // 如果节点x在lo和hi中间,则将节点x添加到队列queue中
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        // 如果hi在节点x的右子树,则递归到节点x的右子树中继续查找
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public int size(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        // 返回hi所在的排名减去lo所在的排名;
        // 如果key为hi的节点在二叉树中存在则结果+1,
        // TODO 这里自己没讲清楚
        // 因为当key不存在时,rank函数实际返回了二叉树中下一个key的排名;lo的rank对结果没有影响,但是hi有影响,具体可以画图验证一下
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    /**
     * Returns the height of the BST (for debugging).
     * 计算树的高度,
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }
    private int height(Node x) {
        // [最后看这行]这样处理的话一个节点的树的高度为0
        if (x == null) return -1;
        // 1 + 最高的子树高度
        return 1 + Math.max(height(x.left), height(x.right));
    }
}