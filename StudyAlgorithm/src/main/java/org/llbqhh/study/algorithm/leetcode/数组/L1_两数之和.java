package org.llbqhh.study.algorithm.leetcode.数组;

import java.util.HashMap;
import java.util.Map;

public class L1_两数之和 {
    public int[] twoSum(int[] nums, int target) {
        int[] re = new int[2];
        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int v = target - nums[i];
            if (index.containsKey(nums[i])) {
                re[0] = index.get(nums[i]);
                re[1] = i;
                break;
            } else {
                index.put(v, i);
            }
        }
        return re;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int target = 7;
        int[] re = new L1_两数之和().twoSum(nums, target);
        System.out.println(re[0] + "," + re[1]);
    }
}
