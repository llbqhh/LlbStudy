package org.llbqhh.study.algorithm.sort;

/**
 * 可参考 https://algs4.cs.princeton.edu/22mergesort/
 */
public class MergeSort extends BaseSort{
    /**
     * Bottom-up mergesort
     * 自底向上的归并
     * 先从最小粒度的元素进行两两归并为小的有序数组，再依次对有序小数组进行两两归并成为新的有序数组，依次类推，最终归并成为一个整体有序数组。
     * @param a
     */
    public static void sortBU(Comparable[] a) {
        //进行自底向上的两两归并
        int n = a.length;
        Comparable[] aux = new Comparable[n];
        for (int len = 1; len < n; len *= 2) {
            for (int lo = 0; lo < n - len; lo += len + len) {
                int mid  = lo + len - 1;
                int hi = Math.min(lo + len + len - 1, n - 1);
                merge(a, aux, lo, mid, hi);
            }
        }
    }

    /**
     * 归并排序（MERGE-SORT）是建立在归并操作上的一种有效的排序算法,该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
     * 将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序(递归调用)。
     *
     *
     * @param a
     */
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    //自顶向下的归并
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid); //将左半边排序
        sort(a, aux, mid + 1, hi); //将右半边排序
        merge(a, aux, lo, mid, hi); //归并结果
    }

    //使用辅助数组进行归并
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        // 将数组a复制到辅助数组中
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // 分别初始化两个数组的指针
        int i = lo;
        int j = mid + 1;
        // merge back to a[]
        // 归并回到a[lo...hi]
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }
    }
}
