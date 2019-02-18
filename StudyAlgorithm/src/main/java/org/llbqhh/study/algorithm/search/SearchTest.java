package org.llbqhh.study.algorithm.search;

import org.junit.Before;
import org.junit.Test;

public class SearchTest {
    // TODO 2-3查找树,红黑树是一种特殊的2-3查找树,
    // 红节点和他的父节点实际上是一个3-节点(也就是红节点代表指向它的链接是红色的,一条红色链接上的两个节点组成一个3-节点)
    @Before
    public void before() {
        for (int i = 0; i < 80; i++) {
            System.out.print("=");
        }
        System.out.println("");
    }

    @Test
    public void testBinarySearch() {
        Integer[] array = {1, 2, 5, 7, 9, 22, 46, 78, 88, 89, 299, 444, 555, 3435};
        System.out.println(BinarySearch.search(88, array));
    }

    @Test
    public void testBST() {
        String test = "S E A R C H E X A M P L E";
        String[] keys = test.split(" ");
        int n = keys.length;
        BinarySearchTree<String, Integer> st = new BinarySearchTree<String, Integer>();
        for (int i = 0; i < n; i++)
            st.put(keys[i], i);

        System.out.println("size = " + st.size());
        System.out.println("min  = " + st.min());
        System.out.println("max  = " + st.max());
        System.out.println();

        // print keys in order using allKeys()
        System.out.println("Testing keys()");
        System.out.println("--------------------------------");
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();

        // print keys in order using select
        System.out.println("Testing select");
        System.out.println("--------------------------------");
        for (int i = 0; i < st.size(); i++)
            System.out.println(i + " " + st.select(i));
        System.out.println();

        // test rank, floor, ceiling
        System.out.println("key rank floor ceil");
        System.out.println("-------------------------");
        for (char i = 'A'; i <= 'Z'; i++) {
            String s = i + "";
            System.out.printf("%2s %4d %4s %4s\n", s, st.rank(s), st.floor(s), st.ceiling(s));
        }
        System.out.println();

        // test range search and range count
        String[] from = { "A", "Z", "X", "0", "B", "C", "A"};
        String[] to   = { "D", "A", "X", "Z", "G", "L", "Z"};
        System.out.println("range search");
        System.out.println("-------------------");
        for (int i = 0; i < from.length; i++) {
            System.out.printf("%s-%s (%2d) : ", from[i], to[i], st.size(from[i], to[i]));
            for (String s : st.keys(from[i], to[i]))
                System.out.print(s + " ");
            System.out.println();
        }
        System.out.println();

        // delete the smallest keys
        for (int i = 0; i < st.size() / 2; i++) {
            st.deleteMin();
        }
        System.out.println("After deleting the smallest " + st.size() / 2 + " keys");
        System.out.println("--------------------------------");
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();

        // delete all the remaining keys
        while (!st.isEmpty()) {
            st.delete(st.select(st.size() / 2));
        }
        System.out.println("After deleting the remaining keys");
        System.out.println("--------------------------------");
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();

        System.out.println("After adding back the keys");
        System.out.println("--------------------------------");
        for (int i = 0; i < n; i++)
            st.put(keys[i], i);
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();
    }

    @Test
    public void testRedBlackLiteBST() {
        String test = "S E A R C H E X A M P L E";
        String[] keys = test.split(" ");
        int n = keys.length;
        RedBlackLiteBST<String, Integer> st = new RedBlackLiteBST<String, Integer>();
        for (int i = 0; i < n; i++)
            st.put(keys[i], i);

        System.out.println("size = " + st.size());
        System.out.println("min  = " + st.min());
        System.out.println("max  = " + st.max());
        System.out.println();

        // print keys in order using allKeys()
        System.out.println("Testing keys()");
        System.out.println("--------------------------------");
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
        System.out.println();
    }

    @Test
    public void testSeparateChainingHashST() {
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        for (int i = 0; i < 10; i++) {
            String key = "key" + i;
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
    }

    @Test
    public void testLinearProbingHashST() {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
        for (int i = 0; i < 10; i++) {
            String key = "key" + i;
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
    }
}
