package org.llbqhh.study.algorithm.sort;

/**
 * 可参考 https://algs4.cs.princeton.edu/23quicksort/
 */
public class QuickSort extends BaseSort{
    /**
     * 通过一趟排序将要排序的数据分割成独立的两部分，
     * 其中一部分的所有数据都不大于切分元素，另外一部分数据不小于切分元素，
     * 然后再按此方法对这两部分数据分别进行快速排序，
     * 整个排序过程可以递归进行，
     * 以此达到整个数据变成有序序列。
     * @param a
     */
    public static void sort(Comparable[] a) {
        simpleShuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        //切分成两部分，在j左边的元素都不大于j元素，在j右边的元素都不小于j元素
        int j = partition(a, lo, hi);
        //将左半部分排序
        sort(a, lo, j - 1);
        //将右半部分排序
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        //将数组切分成a[lo...i-1],a[i],a[i+1...hi]
        int i = lo; //从左到右扫描指针
        int j = hi + 1;  //从右到坐扫描指针
        Comparable v = a[lo]; //切分元素
        while (true) {
            // 从数组左边找一个不小于切分元素的元素
            while (less(a[++i], v)) {
                if (i == hi) break;
            }
            // 从数组右边找一个不大于切分元素的元素
            while (less(v, a[--j])) {
                if (j == lo) break;
            }
            // 检查两边的扫描指针是否已经重叠
            if (i >= j) break;
            // 将i,j位置交换
            exch(a, i, j);
        }
        // 最终两边指针重叠后，将切分元素放入正确位置
        exch(a, lo, j);
        // 返回切分元素索引
        return j;
    }

    /**
     * 优化过的快速排序，将数组分割成小于切分元素、等于切分元素和大于切分元素三个部分。对大于和小于的部分再进行递归处理。
     * @param a
     */
    public static void sort3way(Comparable[] a) {
        simpleShuffle(a);
        sort3way(a, 0, a.length - 1);
    }

    private static void sort3way(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        // 小于标志，小于这个索引的元素都小于切分元素
        int lt = lo;
        // 大于标志，大于这个索引的元素都大于切分元素
        int gt = hi;
        // 切分元素v
        Comparable v = a[lo];
        // 从左向右的移动指针
        int i = lo + 1;
        while (i <= gt) {
            // 比较当前元素和切分元素
            int cmp = a[i].compareTo(v);
            // 如果当前元素小于切分元素，则互换二者位置，并且将lt和i同时加1
            if (cmp < 0) exch(a, lt++, i++);
            // 如果当前元素大于切分元素，则将当前元素和gt互换位置，并将gt减1
            else if (cmp > 0) exch(a, i, gt--);
            // 如果相同则指针右移一位
            else i++;
        }
        // 现在a[lo...lt-1] < v
        // a[lt...gt] = v
        // v < a[gt+1..hi]成立
        // 即数组成为小于切分元素，等于切分元素，大于切分元素三部分
        sort3way(a, lo, lt - 1);
        sort3way(a, gt + 1, hi);
    }
}