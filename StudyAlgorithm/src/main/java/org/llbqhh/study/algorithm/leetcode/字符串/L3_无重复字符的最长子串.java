package org.llbqhh.study.algorithm.leetcode.字符串;

import java.util.HashSet;
import java.util.Set;

public class L3_无重复字符的最长子串 {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 使用滑动窗口来计算，首先定义两个指针
        int start = 0;
        int end = 0;

        // 记录当前计算过的所有子串中的最大长度
        int curMaxLength = 0;

        int length = s.length();

        Set<Character> chars = new HashSet<>();

        while (start < length) {
            // 去掉start指针已经过去的字符
            if (start != 0) {
                chars.remove(s.charAt(start - 1));
            }

            // end指针不能在start前面，会重复计算字符
            while (end < start) {
                end ++;
            }

            // 如果当前的hash表中不存在end指针，则存入
            while (end < length && !chars.contains(s.charAt(end))) {
                chars.add(s.charAt(end));
                end ++;
            }

            // 从当前start位置开始的不重复子串的长度
            // 取得当前hash表的长度
            int curLength = chars.size();

            curMaxLength = Math.max(curLength, curMaxLength);
            start ++;
        }

        return curMaxLength;
    }
}
