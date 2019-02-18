package org.llbqhh.study.algorithm.search;

/**
 * 二分查找
 */
public class BinarySearch {
    /**
     * 首先，假设表中元素是按升序排列，将表中间位置记录的关键字与查找关键字比较，如果两者相等，则查找成功；
     * 否则利用中间位置记录将表分成前、后两个子表，如果中间位置记录的关键字大于查找关键字，则进一步查找前一子表，否则进一步查找后一子表。
     * 重复以上过程，直到找到满足条件的记录，使查找成功，或直到子表不存在为止，此时查找不成功。
     * @param key
     * @param array
     * @return
     */
    public static int search(Comparable key, Comparable[] array) {
        // array需要是有序的
        int low = 0;
        int high = array.length - 1;
        //　如果所查找的key不在数组中可以退出循环
        while (low <= high) {
//            int mid = (high + low) / 2; //这种方式有造成int上限溢出的问题
            int mid = low + (high - low) / 2; //第二种方式避免了high+low大于int上限导致溢出的潜在问题
            //　中间值和key比较:等于key，则直接返回mid索引;如果大于key,则将high左移；小于key，则将low值右移
            int res = array[mid].compareTo(key);
            if (res == 0) {
                return mid;
            } else if (res > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }
}
