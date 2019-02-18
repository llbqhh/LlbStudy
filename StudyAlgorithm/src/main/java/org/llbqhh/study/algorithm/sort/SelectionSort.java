package org.llbqhh.study.algorithm.sort;

public class SelectionSort extends BaseSort{
    /**
     * 它的工作原理是每一次从待排序的数据元素中选出最小（或最大）的一个元素，存放在序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * @param array
     */
    public static void selectionSort(Comparable[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (less(array[j], array[min])) min = j;
            }
            exch(array, i, min);
        }
    }
}
