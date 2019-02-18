package org.llbqhh.study.algorithm.sort;

/**
 *  The {@code Heap} class provides a static methods for heapsorting
 *  an array.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class HeapSort extends BaseSort{
    /**
     * 首先将数组构建成堆，堆首元素最大，再对堆进行操作，依次将最大元素挪到数组末尾，最后取得一个由小到大排列过的数组
     * @param pq the array to be sorted
     */
    public static void sort(Comparable[] pq) {
        // 这个方法中对堆操作时，索引使用从1开始，这样比较容易模拟堆的操作也好理解。
        int n = pq.length;
        // 第一步
        // 将无序堆构建为有序堆，从数组长度的一半开始处理（因为剩下的元素是叶子元素），每次将一个元素下沉到合适位置
        for (int k = n / 2; k >= 1; k--)
            sink(pq, k, n);
        // 第二步
        // 将堆顶位置的元素换到数组最后一位，然后将换到堆首的元素下沉到适当位置
        // 如此循环直到原来堆中元素全部被处理过，此时的数组已经有序
        while (n > 1) {
            // 将堆首元素和堆尾元素互换
            exch(pq, atHeap(1), atHeap(n));
            // 堆长度减1，堆尾元素被移除出堆，被移除出堆的那些元素是有序的
            n--;
            // 将新的堆首元素下沉，保证堆的有序
            sink(pq, 1, n);
        }
    }

    /**
     * 将堆中元素k下沉到合适位置，使得开始以k为堆顶的这个堆变为有序
     * @param pq 堆数组
     * @param k 需要下沉的元素位置
     * @param n 堆长度
     */
    private static void sink(Comparable[] pq, int k, int n) {
        // 这个判断条件是因为元素k有可能会下沉，如果下沉到最下层的叶子节点就不需要再做下沉了，此条件就不再满足
        while (2 * k <= n) {
            // j指向k的左子叶
            int j = 2 * k;
            // 如果左子叶小于右子叶，则将j指向k的右子叶。这样可以保证这三个元素所在的子堆中，堆顶位置元素最大
            if (j < n && less(pq[atHeap(j)], pq[atHeap(j + 1)])) j++;
            // 如果k元素小于j元素，则这个子树有序，退出循环
            if (!less(pq[atHeap(k)], pq[atHeap(j)])) break;
            // 如果k元素大于j元素，则将k元素和j元素互换位置
            exch(pq, atHeap(k), atHeap(j));
            // 这时k元素实际上已经挪到了j元素的位置，所以将索引k指向j。这时如果k元素下面还有叶子节点，则会继续上述操作，满足条件则会继续下沉
            k = j;
        }
    }

    /**
     * 转换堆索引=》数组索引
     * @param heapIndex
     * @return
     */
    private static int atHeap(int heapIndex) {
        // 最终真正进行比较判和交换元素位置时使用真正的数组索引（即堆索引-1）
        return heapIndex - 1;
    }
}