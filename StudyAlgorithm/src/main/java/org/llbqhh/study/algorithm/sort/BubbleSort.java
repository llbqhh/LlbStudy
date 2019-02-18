package org.llbqhh.study.algorithm.sort;

public class BubbleSort extends BaseSort{
    /**
     * 冒泡排序算法的原理如下：
     比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     对每一对相邻元素做同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     针对所有的元素重复以上的步骤，除了最后一个。
     持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     * @param array
     * @return
     */
    public static void bubbleSort(Comparable[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            // 两两比较，将较大元素后移
            for (int j = 1; j < len - i; j++) {
                if (greater(array[j - 1], array[j])) {
                    exch(array, j - 1, j);
                }
            }
        }
    }
}
