package org.llbqhh.study.design.pattern.strategy;

/**
 * 使用策略模式的比较器
 * @author lilibiao
 * @date 2019-12-26 13:40
 */
public class StragegySorter<T> {
    public void sort(T[] o, Comparator<T> c) {
        int len = o.length;
        for (int i = 0; i < len; i++) {
            // 两两比较，将较大元素后移
            for (int j = 1; j < len - i; j++) {
                if (c.compare(o[j - 1], o[j]) > 0) {
                    exch(o, j - 1, j);
                }
            }
        }
    }

    // exchange a[i] and a[j]
    protected static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
