package org.llbqhh.study.algorithm.leetcode.二分查找;

//把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2
//] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
//
// 示例 1：
//
// 输入：[3,4,5,1,2]
//输出：1
//
//
// 示例 2：
//
// 输入：[2,2,2,0,1]
//输出：0
//
//
// 注意：本题与主站 154 题相同：https://leetcode-cn.com/problems/find-minimum-in-rotated-sor
//ted-array-ii/
// Related Topics 二分查找
// 👍 106 👎 0

public class 剑指Offer11_旋转数组的最小数字 {
//    // 遍历做法
//    public int minArray(int[] numbers) {
//        // 原数组是递增的，所以只要找到一个值，比它的前一个值小即可；如果找不到这样一个值，则数组第一个值为最小值
//        for (int i = 1; i < numbers.length; i++) {
//            if (numbers[i - 1] > numbers[i]) {
//                return numbers[i];
//            }
//        }
//        return numbers[0];
//    }

    // 二分查找
    public int minArray(int[] numbers) {
        int low = 0;
        int high = numbers.length - 1;

        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (numbers[pivot] < numbers[high]) {
                high = pivot;
            } else if (numbers[pivot] > numbers[high]) {
                low = pivot + 1;
            } else {
                high -= 1;
            }
        }

        return numbers[low];
    }

    public static void main(String[] args) {
        int[] arr = {3, 4, 5, 1, 2};
        System.out.println(new 剑指Offer11_旋转数组的最小数字().minArray(arr));
    }
}
