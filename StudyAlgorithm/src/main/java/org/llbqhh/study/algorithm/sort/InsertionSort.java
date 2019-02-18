package org.llbqhh.study.algorithm.sort;

public class InsertionSort extends BaseSort{
    /**
     * 插入排序的基本思想是：
     * 每步将一个待排序的记录，按其关键码值的大小插入前面已经排序的文件中适当位置上，直到全部插入完为止。
     * @param array
     */
    public static void insertionSort(Comparable[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            // 将待排序的元素从已排序部分的尾部开始两两比较，较小的元素往前挪
            for (int j = i + 1; j > 0 && j < len; j--) {
                if (less(array[j], array[j - 1])) {
                    exch(array, j, j - 1);
                }
            }
        }
    }
}
