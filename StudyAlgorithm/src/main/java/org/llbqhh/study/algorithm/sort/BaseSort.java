package org.llbqhh.study.algorithm.sort;

import java.util.Comparator;
import java.util.Random;

public class BaseSort {
    // is v < w ?
    protected static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
    }

    // is v > w?
    protected static boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

    // exchange a[i] and a[j]
    protected static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    protected static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // inner
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    /**
     * 将数组打散
     * @param a
     */
    protected static void simpleShuffle(Object[] a) {
        System.out.println("before shuffle: ");
        show(a);
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        for (int i = 0; i < a.length; i++) {
            int swapIndex = r.nextInt(a.length - i) + i;
            exch(a, i, swapIndex);
        }
        System.out.println("after shuffle: ");
        show(a);
    }

    protected static void show(Object[] array) {
        for (Object v : array) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
