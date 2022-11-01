package org.llbqhh.study.algorithm.leetcode.贪心算法;

public class L392_判断子序列 {
    public boolean isSubsequence(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();
        int sIndex = 0;
        int tIndex = 0;
        while (sIndex != sLen && tIndex != tLen) {
            char sChar = s.charAt(sIndex);
            char tChar = t.charAt(tIndex);
            if (sChar == tChar) {
                sIndex++;
                tIndex++;
            } else {
                tIndex++;
            }
        }
        return sIndex == sLen;
    }

    public static void main(String[] args) {
        String s = "ace";
        String t = "abcde";
        System.out.println(new L392_判断子序列().isSubsequence(s, t));
    }
}
