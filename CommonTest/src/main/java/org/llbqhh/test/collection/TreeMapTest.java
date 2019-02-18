package org.llbqhh.test.collection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapTest {
    public static void main(String[] args) {
        TreeMap<String, String> tm = new TreeMap<String, String>();

        tm.put("aaaaaa", "");
        tm.put("bc", "");
        tm.put("aca", "");
        tm.put("aaa", "");
        tm.put("bb", "");
        tm.put("bbb", "");
        tm.put("cc", "");

        String[] sa1 = {"1", "2", "3"};
        String[] sa2 = {"3", "5", "6"};

        System.out.println(Arrays.toString(concat(sa1, sa2)));

        for (String str : tm.keySet()) {
            System.out.println(str);
        }
        System.out.println("=========");
        for (String str : tm.headMap("bbc").keySet()) {
            System.out.println(str);
        }
        System.out.println("=========");
        for (String str : tm.tailMap("bbc").keySet()) {
            System.out.println(str);
        }

        System.out.println("=========");
        for (String str : tm.subMap("bbb", "bbc").keySet()) {
            System.out.println(str);
        }
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static String[] concat(String[] a1, String[] a2) {
        Set<String> set = new HashSet<String>();
        for (String a : a1) {
            set.add(a);
        }
        for (String a : a2) {
            set.add(a);
        }
        String[] a = new String[set.size()];
        return set.toArray(a);
    }
}