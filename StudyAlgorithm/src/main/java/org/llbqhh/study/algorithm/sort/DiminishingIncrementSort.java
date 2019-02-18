package org.llbqhh.study.algorithm.sort;

/**
 * Shell's Sort
 */
public class DiminishingIncrementSort extends BaseSort{
    /**
     * 先取一个小于n的整数d1作为第一个增量，把文件的全部记录分组。
     * 所有距离为d1的倍数的记录放在同一个组中。
     * 先在各组内进行直接插入排序；然后，取第二个增量d2<d1重复上述的分组和排序，
     * 直至所取的增量dt=1，即所有记录放在同一组中进行直接插入排序为止。
     *
     * 希尔排序是基于插入排序的以下两点性质而提出改进方法的：
     插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率。
     但插入排序一般来说是低效的，因为插入排序每次只能将数据移动一位。
     * @param a
     */
    public static void shellSort(Comparable[] a) {
        int length = a.length;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ...
        int n = 1;
        // 初始化增量（距离）
        while (n < length / 3) {
            n = 3 * n + 1;
            System.out.println("N:" + n);
        }

        while (n >= 1) {
            // h-sort the array
            // 对增量数组进行插入排序
            for (int i = n; i < length; i++) {
                for (int j = i; j >= n && less(a[j], a[j - n]); j -= n) {
                    exch(a, j, j - n);
                }
            }
            // 增量递减
            n /= 3;
        }
    }
}
